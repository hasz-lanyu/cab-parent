package org.cab.api.admin.module;

import org.cab.api.user.module.User;

public class UserVo extends User {
    private String deptName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
