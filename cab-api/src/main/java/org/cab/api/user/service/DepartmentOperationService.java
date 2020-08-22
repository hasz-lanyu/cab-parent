package org.cab.api.user.service;

import org.cab.api.user.module.Department;
import org.cab.api.user.module.DepartmentBo;

import java.util.List;

public interface DepartmentOperationService {
    List<Department> getDeptList();

    Long selectDeptManagerIdById(Long id);


    DepartmentBo selectUserTheDepartmentInfoById(Long id);
}
