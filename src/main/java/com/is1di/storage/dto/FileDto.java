package com.is1di.storage.dto;

import lombok.Data;

import java.time.LocalDateTime;

public class FileDto {
    @Data
    public static class Output {
        private String id;
        private String contentUrl;
        private Long size;
        private String fileType;
        private String ownerId;
        private String originalFileName;
        private LocalDateTime createdAt;
    }
}

