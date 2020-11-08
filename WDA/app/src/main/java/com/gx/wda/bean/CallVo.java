package com.gx.wda.bean;

import android.media.AudioManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 通话页面对象
 */
public class CallVo {
    private TextView tvTitle;//对方账号
    private TextView tvHint;//提示
    private LinearLayout llCall;//通话
    private LinearLayout llConnect;//连接
    private LinearLayout llMute;//禁麦
    private LinearLayout llCancel;//取消
    private LinearLayout llHands;//免提
    private LinearLayout llMuteWhite;//禁麦
    private LinearLayout llMuteBlack;//禁麦
    private LinearLayout llHandsWhite;//免提
    private LinearLayout llHandsBlack;//免提
    private LinearLayout llFriendList;//好友列表

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public TextView getTvHint() {
        return tvHint;
    }

    public void setTvHint(TextView tvHint) {
        this.tvHint = tvHint;
    }

    public void setLlCall(LinearLayout llCall) {
        this.llCall = llCall;
    }

    public void setLlConnect(LinearLayout llConnect) {
        this.llConnect = llConnect;
    }

    public void setLlMute(LinearLayout llMute) {
        this.llMute = llMute;
    }

    public void setLlCancel(LinearLayout llCancel) {
        this.llCancel = llCancel;
    }

    public void setLlHands(LinearLayout llHands) {
        this.llHands = llHands;
    }

    public LinearLayout getLlCall() {
        return llCall;
    }

    public LinearLayout getLlConnect() {
        return llConnect;
    }

    public LinearLayout getLlMute() {
        return llMute;
    }

    public LinearLayout getLlCancel() {
        return llCancel;
    }

    public LinearLayout getLlHands() {
        return llHands;
    }

    public LinearLayout getLlMuteWhite() {
        return llMuteWhite;
    }

    public void setLlMuteWhite(LinearLayout llMuteWhite) {
        this.llMuteWhite = llMuteWhite;
    }

    public LinearLayout getLlMuteBlack() {
        return llMuteBlack;
    }

    public void setLlMuteBlack(LinearLayout llMuteBlack) {
        this.llMuteBlack = llMuteBlack;
    }

    public LinearLayout getLlHandsWhite() {
        return llHandsWhite;
    }

    public void setLlHandsWhite(LinearLayout llHandsWhite) {
        this.llHandsWhite = llHandsWhite;
    }

    public LinearLayout getLlHandsBlack() {
        return llHandsBlack;
    }

    public void setLlHandsBlack(LinearLayout llHandsBlack) {
        this.llHandsBlack = llHandsBlack;
    }

    public LinearLayout getLlFriendList() {
        return llFriendList;
    }

    public void setLlFriendList(LinearLayout llFriendList) {
        this.llFriendList = llFriendList;
    }
}
