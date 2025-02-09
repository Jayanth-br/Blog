package org.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


public class Response {

    private boolean status;
    @JsonInclude(Include.NON_NULL)
    private Object data;
    @JsonInclude(Include.NON_NULL)
    private String error;
    private int statusCode;

    public Response(boolean status, Object data, int statusCode) {
        this.status = status;
        this.data = data;
        this.statusCode = statusCode;
    }

    public Response(boolean status,String error, int statusCode) {
        this.status = status;
        this.error = error;
        this.statusCode = statusCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
