package org.cab.activiti.cabactiviti;

import org.activiti.engine.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CabActivitiApplicationListenerTests {

    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    HistoryService historyService;
    @Autowired
    IdentityService identityService;
    @Autowired
    RepositoryService repositoryService;

    final String key = "delegateListener";

    @Test
    public void processStart(){
        runtimeService.startProcessInstanceByKey(key);
    }

    @Test
    public void getTask(){
        String assignee="王五";
        taskService.createTaskQuery().taskAssignee(assignee)
                //卖家发货、
                .list()
                .forEach(task -> {
                    System.out.println("流程定义ID："+task.getProcessDefinitionId());
                    System.out.println("任务名称："+task.getName());
                    System.out.println("流程实例ID："+task.getProcessInstanceId());
                    System.out.println("执行实例ID："+task.getExecutionId());
                    System.out.println("任务id："+task.getId());
                    System.out.println("任务办理人："+task.getAssignee());
                });
    }
    @Test
    public  void completeTask(){
        String taskId = "67518";
        Map<String,Object> var = new HashMap<>();
        var.put("level",0);
        taskService.complete(taskId,var);
    }


}