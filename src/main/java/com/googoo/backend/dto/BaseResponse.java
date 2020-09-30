package com.googoo.backend.dto;

public class BaseResponse<T> {
    private T payload;

    private String error;
    private ResponseCode code;

    public BaseResponse() {
        super();
    }

    public BaseResponse(T payload) {
        super();
        this.payload = payload;
        this.code = ResponseCode.OK;
    }

    public BaseResponse(String error, ResponseCode code) {
        super();
        this.error = error;
        this.code = code;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ResponseCode getCode() {
        return code;
    }

    public void setCode(ResponseCode code) {
        this.code = code;
    }
}
