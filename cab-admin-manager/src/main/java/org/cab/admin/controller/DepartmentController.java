package org.cab.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.cab.api.user.module.Department;
import org.cab.api.user.service.DepartmentOperationService;
import org.cab.common.resp.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/dept")
public class DepartmentController {
    @Reference
    private DepartmentOperationService departmentOperationService;
    @ResponseBody
    @GetMapping
    public Result getDeptList(){
        List<Department> deptList = departmentOperationService.getDeptList();
        return Result.ok(deptList);
    }
}
