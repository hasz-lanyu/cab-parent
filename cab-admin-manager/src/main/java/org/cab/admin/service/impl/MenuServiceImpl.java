package org.cab.admin.service.impl;

import org.cab.admin.mapper.MenuMapper;
import org.cab.admin.service.MenuService;
import org.cab.admin.utils.ObjUtil;
import org.cab.api.admin.module.Menu;
import org.cab.api.admin.module.MenuVo;
import org.cab.common.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;


    @Override
    public List<MenuVo> selectMenuWithChildAll() throws Exception {
//        return comboMenu(menuMapper.selectByExample(null));
/*        List<Menu> menus = menuMapper.selectByExample(null);
        List<MenuVo> list = new ArrayList<>(menus.size());
        for (Menu menu : menus) {
            list.add(ObjUtil.copyObj(menu, MenuVo.class));
        }
        List<MenuVo> menuVos = this.comboMenu(list, "getId", "getPid", "getChild", "setChild");
        return menuVos.stream().filter(menuVo -> menuVo.getPid() == 0).collect(Collectors.toList());*/
        List<Menu> menus = menuMapper.selectByExample(null);
        long start = System.currentTimeMillis();
        List<MenuVo> menuVos = comboMenu(menus);
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - start));
        return menuVos;
    }

    /**
     * 菜单组合
     * 注：此处如果表增加菜单等级字段无需这么复杂
     *
     * @param menus
     * @return
     */
    private List<MenuVo> comboMenu(List<Menu> menus) {
        if (menus == null) {
            throw new CustomException("菜单集合不能为空");
        }
        Map<Long, MenuVo> map = menus.stream().map(menu -> ObjUtil.copyObj(menu, MenuVo.class)).collect(Collectors.toMap
                (menuVo -> menuVo.getId(), menuVo -> menuVo));

        return menus.stream().map(eachMenu -> {
            MenuVo parent = map.get(eachMenu.getId());
            menus.stream().forEach(menu -> {
                if (menu.getPid().equals(eachMenu.getId())) {
                    List<MenuVo> child = parent.getChild() == null ? new ArrayList<>() : parent.getChild();
                    child.add(map.get(menu.getId()));
                    parent.setChild(child);
                }
            });
            return parent;
        }).filter(r -> r.getPid() == 0).collect(Collectors.toList());
    }

    /**
     * @param menus                 集合数据
     * @param getIdMethodName       获取id的方法名
     * @param getParentIdMethodName 获取父id的方法名
     * @param getChildMethodName    获取子属性的方法名
     * @param setChildMethodName    设置子属性的方法名
     * @param <T>                   集合元素的类型
     * @return 返回一个带有子属性的集合 含全量数据 根节点根据自身的需求进行过滤
     * @throws Exception
     */
    private <T> List<T> comboMenu(List<T> menus, String getIdMethodName, String
            getParentIdMethodName, String getChildMethodName, String setChildMethodName) throws
            Exception {
        if (menus == null || menus.isEmpty()) {
            throw new CustomException("集合不能为空");
        }
        long start = System.currentTimeMillis();
        //获取泛型类型
        T t = menus.get(0);
        //通过字节码对象 反射获取方法
        Method getId = t.getClass().getMethod(getIdMethodName);
        Method getPid = t.getClass().getMethod(getParentIdMethodName);
        Method getChild = t.getClass().getMethod(getChildMethodName);
        Method setChild = t.getClass().getMethod(setChildMethodName, List.class);
        //hashmap 作为临时存储元素
        Map<Object, T> map = new HashMap<>(menus.size());
        for (int i = 0; i < menus.size(); i++) {
            Object id = getId.invoke(menus.get(i));
            map.put(id, menus.get(i));
        }

        //2次遍历组装父子关系
        for (T patents : menus) {
            //拿到每个元素id
            Object pid = getId.invoke(patents);
            T parent = map.get(pid);
            for (T childs : menus) {
                //每个元素父id跟元素id进行判断
                Object childPid = getPid.invoke(childs);
                if (childPid.equals(pid)) {
                    // childPid.equals(pid) 如果元素的pid 与另一个元素的id吻合 代表是他的子
                    //判断元素的子属性是否为空 如果是空 创建一个新的 不为空 使用原始的
                    List<T> childList = getChild.invoke(parent) == null ? new ArrayList<>() : (List<T>) getChild.invoke(parent);
                    Object childId = getId.invoke(childs);
                    childList.add(map.get(childId));
                    setChild.invoke(parent, childList);
                }
            }
        }

        List<T> collect = map.values().parallelStream().collect(Collectors.toList());
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - start));
        return collect;
    }
}
