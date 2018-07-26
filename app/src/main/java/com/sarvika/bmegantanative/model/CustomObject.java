package com.sarvika.bmegantanative.model;

public class CustomObject {
    private String name;
    private String vid;
    private String cid;
    private String position;
    private String description;
    private String URL;
    private int Sequence;

    public CustomObject(String name, String vid, String cid, String position, String description, String URL, int Sequence) {
        this.name = name;
        this.vid = vid;
        this.cid = cid;
        this.position = position;
        this.description = description;
        this.URL = URL;
        this.Sequence = Sequence;
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

    public int getSequence() {
        return Sequence;
    }

    public void setSequence(int sequence) {
        Sequence = sequence;
    }
}
