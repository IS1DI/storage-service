package com.is1di.storage.utils;

import lombok.Getter;

@Getter
public enum MessageMethod {

    FILE_NOT_FOUND("file.error.notFound"),
    FILE_UPLOAD_ERROR("file.error.fileUpload"),
    FILE_RETRIEVE_ERROR("file.error.fileRetrieve");

    private final String val;

    MessageMethod(String val) {
        this.val = val;
    }

}
