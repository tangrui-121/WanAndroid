package com.example.library.bean;

import com.alibaba.fastjson.JSONObject;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

import java.io.Serializable;

/**
 * Created by Mr'Tang on 2018/5/22.
 */

@Entity
public class LoginData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    private long id;
    private String username;
    private String password;
    private String icon;
    private String email;
    private int type;

    @Keep
    @Generated(hash = 1872260079)
    public LoginData(long id, String username, String password, String icon,
                     String email, int type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.icon = icon;
        this.email = email;
        this.type = type;
    }

    @Generated(hash = 1578814127)
    public LoginData() {
    }

    public static LoginData parseFromJson(JSONObject jsonObject) {
        LoginData loginData = new LoginData();
        loginData.setUsername(jsonObject.getString("username"));
        loginData.setPassword(jsonObject.getString("password"));
        loginData.setIcon(jsonObject.getString("icon"));
        loginData.setEmail(jsonObject.getString("email"));
        loginData.setType(jsonObject.getIntValue("type"));
        loginData.setId(jsonObject.getIntValue("id"));
        return loginData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
