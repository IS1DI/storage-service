package com.is1di.storage.service;

import com.is1di.storage.repository.FileRepository;
import com.is1di.storage.entity.files.File;
import com.is1di.storage.exception.NotFoundException;
import com.is1di.storage.utils.MessageMethod;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final FileManagerService fileManagerService;
    private final MessageService messageService;

    public File findFileById(ObjectId id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        messageService.getMessage(MessageMethod.FILE_NOT_FOUND,id.toString())
                ));
    }

    public File upload(File fileEntity, MultipartFile file) {
        fileEntity.setFileRef(fileManagerService.createFile(file)); //TODO thumb
        return fileRepository.save(fileEntity);
    }
}