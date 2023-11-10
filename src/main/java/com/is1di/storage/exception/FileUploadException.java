package com.is1di.storage.exception;

public class FileUploadException extends RuntimeException {
    public FileUploadException() {
    }

    public FileUploadException(String message) {
        super(message);
    }
}
