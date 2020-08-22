package org.cab.admin.service;

import org.cab.api.admin.module.MenuVo;

import java.util.List;

public interface MenuService {
    List<MenuVo> selectMenuWithChildAll() throws Exception;
}
