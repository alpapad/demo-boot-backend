package com.googoo.backend.dto;

import com.googoo.backend.model.Tutorial;

public class TutorialResponse extends BaseResponse<Tutorial> {

    public TutorialResponse() {
        super();
    }

    public TutorialResponse(String error, ResponseCode code) {
        super(error, code);
    }

    public TutorialResponse(Tutorial payload) {
        super(payload);
    }

}
