package org.cab.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.cab.api.user.module.Department;
import org.cab.api.user.module.DepartmentBo;
import org.cab.api.user.service.DepartmentOperationService;
import org.cab.user.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service
@Component
public class DepartmentOperationServiceImpl  implements DepartmentOperationService {
    @Autowired
    private DepartmentMapper departmentMapper;


    @Override
    public List<Department> getDeptList() {
        return departmentMapper.selectByExample(null);
    }

    @Override
    public Long selectDeptManagerIdById(Long id) {
       Long managerId = departmentMapper.selectManagerIdById(id);
        return null;
    }

    @Override
    public DepartmentBo selectUserTheDepartmentInfoById(Long id) {
       DepartmentBo departmentBo = departmentMapper.selectDepartmentWithParentDepartmentById(id);
        return departmentBo;
    }
}
