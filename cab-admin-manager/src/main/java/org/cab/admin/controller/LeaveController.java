/*
package org.cab.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.shiro.SecurityUtils;
import org.cab.admin.service.LeaveService;
import org.cab.api.admin.module.Leave;
import org.cab.api.user.module.DepartmentBo;
import org.cab.api.user.module.User;
import org.cab.api.user.service.DepartmentOperationService;
import org.cab.api.user.service.UserOperationService;
import org.cab.common.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("leave")
public class LeaveController {
    */
/**
     * 执行管理
     *//*


    */
/**
     * 任务管理
     *//*


    */
/**
     * 流程引擎
     *//*


    @Autowired
    private LeaveService leaveService;

    @Reference
    private UserOperationService userOperationService; 人事

    @Reference
    private DepartmentOperationService departmentOperationService;


    private static final String LEAVE_PROCESS_INSTANCE_KEY="leaveProcess";

    @PostMapping("commit")
    public Result startLeaveProcess (@RequestBody Leave leave){
        //shiro 获取当前登录的用户信息
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        leave.setUserId(user.getId());
        //拿用户部门id去查询dept表 DepartmentManager就是该用户的领导
        DepartmentBo departmentBo =  departmentOperationService.selectUserTheDepartmentInfoById(user.getDeptId());
        Long leaderId =departmentBo.getManagerId();
        if (user.getId().equals(leaderId)){
            //如果该用户就是该部门的领导 找该部门的上一级部门的领导 就是该用户的领导
            //该用户leaderId是null 表明是顶级领导 无需理会 由员工等级控制流程走向 此处只考虑找到2级员工的领导
            leaderId=departmentBo.getDepartment().getManagerId();
        }
        leave.setUserId(user.getId());
        Long leaveId  =leaveService.insertLeave(leave);
        Map<String,Object> param = new HashMap<>();
        param.put("leader",leaderId);
        param.put("level",user.getLevelId());
        param.put("leaveId",leaveId);
 */
/*       ProcessInstance instance = runtimeService.startProcessInstanceByKey(LEAVE_PROCESS_INSTANCE_KEY, param);
        String id = instance.getId();
        leave.setProcessInstanceId(id);*//*

        return  null;

    }
}
*/
