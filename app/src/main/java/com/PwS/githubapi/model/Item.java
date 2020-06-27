package com.PwS.githubapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PwS
 */

//POJO
public class Item {

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("avatar_url")
    @Expose
    private String imgUserUrl;

    @SerializedName("html_url")
    @Expose
    private String htmlUrl;

    public Item(String login, String imgUserUrl, String htmlUrl) {
        this.login = login;
        this.imgUserUrl = imgUserUrl;
        this.htmlUrl = htmlUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getImgUserUrl() {
        return imgUserUrl;
    }

    public void setImgUserUrl(String imgUserUrl) {
        this.imgUserUrl = imgUserUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }
}
