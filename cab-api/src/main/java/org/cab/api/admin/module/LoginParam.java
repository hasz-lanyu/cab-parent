package org.cab.api.admin.module;




import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class LoginParam implements Serializable {
    private static final long serialVersionUID = 2514141906415897993L;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Length(min = 3,max = 12,message = "账户长度必须3-12位")
    private String username;
    @Length(min = 3,max = 12,message = "密码长度必须3-12位")
    private String password;

    private boolean rememberMe;



}
