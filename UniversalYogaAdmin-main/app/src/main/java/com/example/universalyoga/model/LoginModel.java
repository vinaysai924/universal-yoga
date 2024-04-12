package com.example.universalyoga.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("data")
    @Expose
    private LoginData data;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "accessToken='" + accessToken + '\'' +
                ", data=" + data +
                ", status=" + status +
                '}';
    }
}
