package com.googoo.backend.dto;

import java.util.List;

import com.googoo.backend.model.Tutorial;

public class TutorialListResponse extends BaseResponse<List<Tutorial>> {

    public TutorialListResponse() {
        super();
    }

    public TutorialListResponse(List<Tutorial> payload) {
        super(payload);
    }

    public TutorialListResponse(String error, ResponseCode code) {
        super(error, code);
    }

}
