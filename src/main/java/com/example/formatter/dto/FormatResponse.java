package com.example.formatter.dto;

public class FormatResponse {
    private boolean success;
    private String message;
    private String result;

    public FormatResponse() {}
    public FormatResponse(boolean success, String message, String result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
}