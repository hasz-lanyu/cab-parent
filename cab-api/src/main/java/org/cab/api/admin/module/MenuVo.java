package org.cab.api.admin.module;

import java.util.List;

public class MenuVo extends Menu {
    private List<MenuVo> child ;

    public List<MenuVo> getChild() {
        return child;
    }

    public void setChild(List<MenuVo> child) {
        this.child = child;
    }
}
