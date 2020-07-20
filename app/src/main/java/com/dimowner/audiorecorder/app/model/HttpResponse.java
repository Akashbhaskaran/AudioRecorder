package com.dimowner.audiorecorder.app.model;

public class HttpResponse {
    public HttpResponse(boolean error, String msg) {
        this.error = error;
        this.msg = msg;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    boolean error;
    String msg;
}
