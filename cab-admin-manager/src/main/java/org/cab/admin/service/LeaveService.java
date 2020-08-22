package org.cab.admin.service;

import org.cab.api.admin.module.Leave;
import org.cab.api.user.module.DepartmentBo;
import org.cab.api.user.module.User;

import java.util.List;
import java.util.Map;

public interface LeaveService {
    Long insertLeave(Leave leave);

    void startLeaveProcess(Leave leave,Map<String,Object> variables,User user);

    void updateLeaveById(Leave leave);

     DepartmentBo selectLeadIdByDeptId(Long deptId);

    List<Leave> getTask(Long userId) ;

    void completeTask(String taskId);
}
