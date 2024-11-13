package com.etms.worldline.payload.response;

public class MessageResponse {
    private int response_code;
    private String message;

    public MessageResponse(int response_code,String message) {
        this.response_code=response_code;
        this.message = message;
    }
    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }
}
