package com.cheart.do12306.app.domain;

/**
 * Created by cheart on 4/25/2014.
 */
public class BaseQueryResult {

    String validateMessagesShowId;
    boolean status;
    int httpstatus;
    Object[] data;
    String[] messages;
    String[] validateMessages;
    public String getValidateMessagesShowId() {
        return validateMessagesShowId;
    }
    public void setValidateMessagesShowId(String validateMessagesShowId) {
        this.validateMessagesShowId = validateMessagesShowId;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public int getHttpstatus() {
        return httpstatus;
    }
    public void setHttpstatus(int httpstatus) {
        this.httpstatus = httpstatus;
    }
    public Object[] getData() {
        return data;
    }
    public void setData(Object[] data) {
        this.data = data;
    }
    public String[] getMessages() {
        return messages;
    }
    public void setMessages(String[] messages) {
        this.messages = messages;
    }
    public String[] getValidateMessages() {
        return validateMessages;
    }
    public void setValidateMessages(String[] validateMessages) {
        this.validateMessages = validateMessages;
    }



}

