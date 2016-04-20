package com.yinuo.mode;

/**
 * Created by ludexiang on 2016/4/20.
 */
public class WorkspaceOptionModel {
    private String mOptionImg;
    private String mOptionTxt;
    private int mOptionId;

    public int getOptionId() {
        return mOptionId;
    }

    public void setOptionId(int optionId) {
        this.mOptionId = optionId;
    }

    public String getOptionImg() {
        return mOptionImg;
    }

    public void setOptionImg(String optionImg) {
        this.mOptionImg = optionImg;
    }

    public String getOptionTxt() {
        return mOptionTxt;
    }

    public void setOptionTxt(String optionTxt) {
        this.mOptionTxt = optionTxt;
    }
}
