package com.is1di.storage.entity.files;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Data
public class File {
    @Id
    private ObjectId id;

    private ObjectId fileRef;
    private Long size;
    private String fileType;
    private String originalFileName;
    @CreatedDate
    private LocalDateTime createdAt;
    private String ownerId;
}
