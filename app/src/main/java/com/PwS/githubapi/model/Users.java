package com.PwS.githubapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Users {
    @SerializedName("items")
    @Expose
    private List<Item> items;

    @SerializedName("total_count")
    @Expose
    private int totalCount;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
