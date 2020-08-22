
package org.cab.admin.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.cab.admin.mapper.LeaveMapper;
import org.cab.admin.service.LeaveService;
import org.cab.api.admin.module.Leave;
import org.cab.api.user.module.DepartmentBo;
import org.cab.api.user.module.User;
import org.cab.api.user.service.DepartmentOperationService;
import org.cab.common.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaveServiceImpl implements LeaveService {

/**
     * 执行管理
     */

    @Autowired
    private RuntimeService runtimeService;

/**
     * 任务管理
     */

    @Autowired
    private TaskService taskService;

/**
     * 流程引擎
     */

    @Autowired
    private ProcessEngine processEngine;

    @Reference
    private DepartmentOperationService departmentOperationService;


    private static final String LEAVE_PROCESS_INSTANCE_KEY = "leaveProcess4";

    @Autowired
    private LeaveMapper leaveMapper;


/**
     * 添加 leave
     * @param leave
     * @return
     */

    @Override
    public Long insertLeave(Leave leave) {
       // leave.setCommintTime(new Date());
      //  leave.setStatus(1);
        if (leaveMapper.insert(leave) != 1) {
            throw new CustomException("leave添加失败");
        }
        return leave.getId();
    }

    public DepartmentBo  selectLeadIdByDeptId(Long deptId){
        //拿用户部门id去查询dept表 DepartmentManager就是该用户的领导
        return departmentOperationService.selectUserTheDepartmentInfoById(deptId);

    }


/**
     * 用户启动流程
     *
     * @param leave     leave
     * @param variables 流程走向变量
     * @param user      当前用户信息
     */

    @Override
    public void startLeaveProcess(Leave leave, Map<String, Object> variables, User user) {
        DepartmentBo departmentBo = selectLeadIdByDeptId(user.getDeptId());
        leave.setUserId(user.getId());
        //调用本类其他事务方法 aop无法切入 this调用 事务会失效 需要拿到aop代理对象执行
//        LeaveServiceImpl leaveServiceImpl = (LeaveServiceImpl) AopContext.currentProxy();
  //      Long leaveId = leaveServiceImpl.insertLeave(leave);
        Long leaveId = this.insertLeave(leave);
        leave.setId(leaveId);
        Map<String, Object> param = new HashMap<>();
        param.put("group_leader", departmentBo.getManagerId());
        param.put("dept_leader", departmentBo.getDepartment()==null?null:departmentBo
                .getDepartment().getManagerId());
        param.put("level", user.getLeaderId());
        //第2个参数 流程关联关联业务id
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(LEAVE_PROCESS_INSTANCE_KEY, leaveId.toString(), param);
        String id = instance.getId();
        leave.setProcessInstanceId(id);
        this.updateLeaveById(leave);

    }

    @Override
    public void updateLeaveById(Leave leave) {
        if (leaveMapper.updateByPrimaryKeySelective(leave) != 1) {
            throw new CustomException("leave修改失败");
        }
    }



/**
     * 获取该用户的流程任务
     *
     * @param userId
     */

    public List<Leave> getTask(Long userId) {
        List<Task> list = taskService.createTaskQuery()
                //任务查询条件
                .taskAssignee(userId.toString())
                //根据创建时间进行排序
                .orderByTaskCreateTime()
                .desc()
                //返回的结果集
                .list();

        if (CollectionUtils.isEmpty(list)) {
            throw new CustomException("");
        }
        return list.stream().map(task -> {
            //获取流程实例id
            String instanceId = task.getProcessInstanceId();
            //根据流程实例id查询出流程实例
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId)
                    .singleResult();
            //拿到流程实例关联的业务id
            String businessKey = processInstance.getBusinessKey();
            //根据业务id查询出leave
            Leave leave = selectLeaveById(Long.parseLong(businessKey));
            if (leave!=null){
                leave.setTaskId(task.getId());
            }
            return leave;
        }).collect(Collectors.toList());
    }


/**
     * 查询leave
     *
     * @param id
     * @return
     */

    public Leave selectLeaveById(Long id) {
        return leaveMapper.selectByPrimaryKey(id);
    }


/**
     * 完成任务
     *
     * @param instId 实例eID
     */

    public void completeTask(String instId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instId).singleResult();
        String id = processInstance.getId();
        Task task = taskService.createTaskQuery().processInstanceId(id).singleResult();


        taskService.complete(task.getId());

    }

}

