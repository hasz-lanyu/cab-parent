package org.cab.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.cab.api.user.module.Department;
import org.cab.api.user.module.DepartmentBo;
import org.cab.api.user.module.DepartmentExample;

public interface DepartmentMapper {
    long countByExample(DepartmentExample example);

    int deleteByExample(DepartmentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectByExample(DepartmentExample example);

    Department selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByExample(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    String selectNameById(Long id);

    Long selectManagerIdById(Long id);

    DepartmentBo selectDepartmentWithParentDepartmentById(Long id);

}