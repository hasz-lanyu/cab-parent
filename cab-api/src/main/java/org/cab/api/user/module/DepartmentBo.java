package org.cab.api.user.module;

/**
 * 一个带有父部门的实体类
 */
public class DepartmentBo extends Department {
    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
