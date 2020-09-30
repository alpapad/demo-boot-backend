package com.googoo.backend.dto;

public class EmptyResponse extends BaseResponse<Void> {

    public EmptyResponse() {
        super(null, ResponseCode.OK);
    }
    
    public EmptyResponse(ResponseCode code) {
        super(null, code);
    }

    public EmptyResponse(String error, ResponseCode code) {
        super(error, code);
    }

}
