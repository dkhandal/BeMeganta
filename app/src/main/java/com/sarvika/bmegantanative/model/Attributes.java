package com.sarvika.bmegantanative.model;

public class Attributes {

    private String attributee_id;
    private String attribute_name;
    private String attribute_type;
    private String attribute_dataname;
    private String attribute_screenname;
    private String attribute_dropname;
    private AttributeDataValue data_value;

    public String getAttributee_id() {
        return attributee_id;
    }

    public void setAttributee_id(String attributee_id) {
        this.attributee_id = attributee_id;
    }

    public String getAttribute_name() {
        return attribute_name;
    }

    public void setAttribute_name(String attribute_name) {
        this.attribute_name = attribute_name;
    }

    public String getAttribute_type() {
        return attribute_type;
    }

    public void setAttribute_type(String attribute_type) {
        this.attribute_type = attribute_type;
    }

    public String getAttribute_dataname() {
        return attribute_dataname;
    }

    public void setAttribute_dataname(String attribute_dataname) {
        this.attribute_dataname = attribute_dataname;
    }

    public String getAttribute_screenname() {
        return attribute_screenname;
    }

    public void setAttribute_screenname(String attribute_screenname) {
        this.attribute_screenname = attribute_screenname;
    }

    public String getAttribute_dropname() {
        return attribute_dropname;
    }

    public void setAttribute_dropname(String attribute_dropname) {
        this.attribute_dropname = attribute_dropname;
    }

    public AttributeDataValue getData_value() {
        return data_value;
    }

    public void setData_value(AttributeDataValue data_value) {
        this.data_value = data_value;
    }
}
