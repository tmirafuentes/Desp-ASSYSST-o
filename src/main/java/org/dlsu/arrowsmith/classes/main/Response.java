package org.dlsu.arrowsmith.classes.main;

public class Response {
    private String status;
    private Object data;
    private Object message;

    public Response() {
    }

    public Response(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Response(String status, Object data, Object message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
