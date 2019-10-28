package com.yy.electric.maintenance.feature.login;

import com.yy.electric.maintenance.base.BaseResult;

public class LoginRequest extends BaseResult {

    public String username;

    public String password;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
