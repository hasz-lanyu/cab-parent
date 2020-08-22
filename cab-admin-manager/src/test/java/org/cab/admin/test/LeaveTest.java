package org.cab.admin.test;

import org.cab.admin.mapper.LeaveMapper;
import org.cab.admin.service.LeaveService;
import org.cab.admin.service.impl.LeaveServiceImpl;
import org.cab.api.admin.module.Leave;
import org.cab.api.user.module.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest

public class LeaveTest {
    @Autowired
    LeaveService leaveService;

    @Autowired
    LeaveMapper leaveMapper;
    @Test
    public void leaveTest(){
        Leave leave = new Leave();
        leave.setTitle("工作稳定");
        long id = 39;
        leave.setUserId(id);
       leave.setDay(3);
        leave.setContent("入职互联网 12K");
        User user = new User();
        user.setId(39L);
        user.setDeptId(17L);
        user.setLeaderId(1L);
        leaveService.startLeaveProcess(leave,null,user); /*id:29
        是咧id+:55001
        任务+:55012*/

    }
    @Test

    public void taskTest(){
        List<Leave> task = leaveService.getTask(33L);
        task.forEach(t->{
            System.out.println("id:"+t.getId());
            System.out.println("是咧id+:"+t.getProcessInstanceId());
            System.out.println("任务+:"+t.getTaskId());

        });
    }

    @Test
    public void completeTaskTest(){
        leaveService.completeTask(75001+"");
        System.out.println("任务完成");
    }
}
