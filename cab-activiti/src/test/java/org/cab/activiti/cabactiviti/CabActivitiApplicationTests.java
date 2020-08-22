package org.cab.activiti.cabactiviti;

import org.activiti.engine.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CabActivitiApplicationTests {

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

    final String key = "myProcess_1";

    @Test
    public void contextLoads() {
        runtimeService.startProcessInstanceByKey(key);
    }
    @Test
    public void getTask() {
        String assignee="张三";
        taskService.createTaskQuery()
                .taskAssignee(assignee)
                .list()
                .forEach(task -> {
                    System.out.println("任务id:"+task.getId());
                    System.out.println("流程实例Id:"+task.getProcessInstanceId());
                    System.out.println("流程定义id:"+task.getProcessDefinitionId());
                    System.out.println("流程执行Id:"+task.getExecutionId());
                    System.out.println("流程办理人:"+task.getAssignee());

                });
    }

    @Test
    public void completeTask(){
        String taskId="5002";
        taskService.complete(taskId);
        System.out.println("任务完成");
    }

    @Test
    public void processInstanceTest(){
        repositoryService.createProcessDefinitionQuery().processDefinitionKey("myProcess_1")
                .list()
                .forEach(processDefinition->{
                    System.out.println("id:"+processDefinition.getId());
                    System.out.println("key:"+processDefinition.getKey());
                    System.out.println("name:"+processDefinition.getName());
                    System.out.println("DiagramResourceName:"+processDefinition.getDiagramResourceName());
                    System.out.println("ResourceName:"+processDefinition.getResourceName());//bpm

                    System.out.println("EngineVersion:"+processDefinition.getEngineVersion());
                    System.out.println("Version:"+processDefinition.getVersion());
                    System.out.println("Category:"+processDefinition.getCategory());
                    System.out.println("getDeploymentId:"+processDefinition.getDeploymentId());
                });
    }
    @Test
    public  void  processDeployment(){
        repositoryService.createDeploymentQuery()
                .list()
                .forEach(deployment -> {
                    System.out.println("流程部署Id:"+deployment.getId());
                    System.out.println("流程部署名称:"+deployment.getName());
                    System.out.println("流程部署时间:"+deployment.getDeploymentTime());
                    System.out.println("流程部署Key:"+deployment.getKey());
                    System.out.println("流程部署category:"+deployment.getCategory());

    });

}
}