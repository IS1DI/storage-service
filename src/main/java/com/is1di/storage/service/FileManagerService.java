package com.is1di.storage.service;

import com.is1di.storage.exception.FileUploadException;
import com.is1di.storage.exception.NotFoundException;
import com.is1di.storage.utils.MessageMethod;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class FileManagerService {
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;
    private final MessageService messageService;

    public ObjectId createFile(MultipartFile file) {
        DBObject metadata = new BasicDBObject();
        try {
            return gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metadata);
        } catch (Exception ex) {
            throw new FileUploadException(
                    messageService.getMessage(MessageMethod.FILE_UPLOAD_ERROR)
            );
        }
    }

    public byte[] getFile(ObjectId id) {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        if (file == null) {
            throw new NotFoundException(
                    messageService.getMessage(MessageMethod.FILE_NOT_FOUND, id.toString())
            );
        }
        try {
            return IOUtils.toByteArray(gridFsOperations.getResource(file).getInputStream());
        } catch (Exception ex) {
            throw new FileUploadException(
                    messageService.getMessage(MessageMethod.FILE_RETRIEVE_ERROR)
            );
        }
    }
}
