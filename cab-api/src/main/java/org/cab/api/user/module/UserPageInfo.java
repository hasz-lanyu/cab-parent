package org.cab.api.user.module;

import org.cab.api.admin.module.UserVo;

import java.io.Serializable;
import java.util.List;

public class UserPageInfo implements Serializable {
    private static final long serialVersionUID = -5022303154654713208L;

    public List<UserVo> getUser() {
        return user;
    }

    public void setUser(List<UserVo> user) {
        this.user = user;
    }

    public Long getCounts() {
        return counts;
    }

    public void setCounts(Long counts) {
        this.counts = counts;
    }

    private List<UserVo> user;
    private  Long counts;

}
