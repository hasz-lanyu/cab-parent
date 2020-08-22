package org.cab.activiti.cabactiviti.config;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component("activitiListener")
public class ActivitiListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("进来了6666666666666");
        String assignee ="王五";
        delegateTask.setAssignee(assignee);
    }
}
