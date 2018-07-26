package com.sarvika.bmegantanative.model;

import java.util.ArrayList;

public class CategoryBean {

    private String name;
    private String vid;
    private String cid;
    private String position;
    private String description;
    private String URL;

    private Prop properties;

    private ArrayList<CategoryBean> child;

    public ArrayList<CategoryBean> getChild() {
        return child;
    }

    public void setChild(ArrayList<CategoryBean> child) {
        this.child = child;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Prop getProperties() {
        return properties;
    }

    public void setProperties(Prop properties) {
        this.properties = properties;
    }


}


