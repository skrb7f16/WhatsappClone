package com.skrb7f16.firebasepractice.Models;

public class GroupMessageModel {
    private String uId;
    private String message;
    private Long timestamp;
    private String senderName;

    public GroupMessageModel() {
    }

    public GroupMessageModel(String uId, String message, Long timestamp, String senderName) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
        this.senderName = senderName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
