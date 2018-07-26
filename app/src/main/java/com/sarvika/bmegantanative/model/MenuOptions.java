package com.sarvika.bmegantanative.model;

import com.sarvika.bmegantanative.R;

public class MenuOptions {
    private int itemId,thumbnailUrl;
    private String text;
    private boolean isShow;
    private boolean isShowWithIcon;

    public MenuOptions() {
    }

    public MenuOptions(int itemId, int thumbnailUrl,String text,boolean isShow,boolean isShowWithIcon) {
        this.itemId = itemId;
        this.text = text;
        this.thumbnailUrl = thumbnailUrl;
        this.isShow = isShow;
        this.isShowWithIcon = isShowWithIcon;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(int thumbnailUrl) {
        if(thumbnailUrl == 0){
            this.thumbnailUrl = R.drawable.ic_login_24dp;
        }else if(thumbnailUrl == 1){
            this.thumbnailUrl = R.drawable.ic_search_black_24dp;
        }else if(thumbnailUrl == 2){
            this.thumbnailUrl = R.drawable.ic_shopping_cart_black_24dp;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String txt) {
        this.text = txt;
    }

    public boolean getIsShow(){
        return this.isShow;
    }

    public void setIsShow(boolean isShow){
        this.isShow = isShow;
    }

    public boolean getIsShowWithIcon(){
        return this.isShowWithIcon;
    }

    public void setIsShowWithIcon(boolean isShowWithIcon){
        this.isShowWithIcon = isShowWithIcon;
    }

}