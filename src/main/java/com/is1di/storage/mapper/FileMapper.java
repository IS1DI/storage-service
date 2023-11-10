package com.is1di.storage.mapper;

import com.is1di.storage.config.ServerUrlConfig;
import com.is1di.storage.dto.FileDto;
import com.is1di.storage.entity.files.File;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
@Mapper(componentModel = "spring")
public abstract class FileMapper {
    @Autowired
    private ServerUrlConfig serverUrlConfig;

    @Mapping(target = "size", expression = "java(file.getSize())")
    @Mapping(target = "originalFileName", expression = "java(file.getOriginalFilename())")
    @Mapping(target = "fileType", expression = "java(file.getContentType())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    public abstract File toFile(MultipartFile file, String ownerId);

    @Mapping(target = "contentUrl", qualifiedByName = "retrieveUrl", source = "id")
    public abstract FileDto.Output toOutput(File file);

    @Named("retrieveUrl")
    public String retrieveUrl(ObjectId id) {
        return serverUrlConfig.getServerUrl() + "/api/file/content/" + id.toString();
    }

    public ObjectId toId(String id) {
        return new ObjectId(id);
    }

    public String toStr(ObjectId id) {
        if (id != null)
            return id.toString();
        return null;
    }
}
