package com.sarvika.bmegantanative.model;

public class AttributeDataValue {
    private String optionid;
    private String ddtext;
    private String code;

    public String getOptionid() {
        return optionid;
    }

    public void setOptionid(String optionid) {
        this.optionid = optionid;
    }

    public String getDdtext() {
        return ddtext;
    }

    public void setDdtext(String ddtext) {
        this.ddtext = ddtext;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    @Override
    public String toString() {
        return ddtext;
    }


}
