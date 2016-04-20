package com.yinuo.mode;

import com.yinuo.base.BaseObject;

/**
 * Created by ludexiang on 2016/4/20.
 */
public class WorkspacePageModel extends BaseObject {
    private int mWorkspaceId;
    private String mWorkspaceImg;
    private String mWorkspaceTitle;
    private String[] mWorkspaceTags;
    private String mWorkspaceLocation;
    private String[] mWorkspaceExtra;
    private String mWorkspacePrice;

    public String[] getWorkspaceExtra() {
        return mWorkspaceExtra;
    }

    public void setWorkspaceExtra(String[] workspaceExtra) {
        this.mWorkspaceExtra = workspaceExtra;
    }

    public int getWorkspaceId() {
        return mWorkspaceId;
    }

    public void setWorkspaceId(int workspaceId) {
        this.mWorkspaceId = workspaceId;
    }

    public String getWorkspaceImg() {
        return mWorkspaceImg;
    }

    public void setWorkspaceImg(String workspaceImg) {
        this.mWorkspaceImg = workspaceImg;
    }

    public String getWorkspaceLocation() {
        return mWorkspaceLocation;
    }

    public void setWorkspaceLocation(String workspaceLocation) {
        this.mWorkspaceLocation = workspaceLocation;
    }

    public String getWorkspacePrice() {
        return mWorkspacePrice;
    }

    public void setWorkspacePrice(String workspacePrice) {
        this.mWorkspacePrice = workspacePrice;
    }

    public String[] getWorkspaceTags() {
        return mWorkspaceTags;
    }

    public void setWorkspaceTags(String[] workspaceTags) {
        this.mWorkspaceTags = workspaceTags;
    }

    public String getWorkspaceTitle() {
        return mWorkspaceTitle;
    }

    public void setWorkspaceTitle(String workspaceTitle) {
        this.mWorkspaceTitle = workspaceTitle;
    }

}
