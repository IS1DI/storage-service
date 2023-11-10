package com.is1di.storage.controller;

import com.is1di.storage.dto.FileDto;
import com.is1di.storage.entity.files.File;
import com.is1di.storage.mapper.FileMapper;
import com.is1di.storage.service.FileManagerService;
import com.is1di.storage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class FileController {
    private final FileService fileService;
    private final FileMapper fileMapper;
    private final FileManagerService fileManagerService;

    @GetMapping("file/info/{id}")
    public FileDto.Output getInfo(@PathVariable ObjectId id) {
        return fileMapper.toOutput(fileService.findFileById(id));
    }

    @GetMapping("file/content/{id}")
    public ResponseEntity<Resource> content(@PathVariable ObjectId id) {
        File file = fileService.findFileById(id);
        byte[] f = fileManagerService.getFile(file.getFileRef());
        return ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalFileName() + "\"")
                .body(new ByteArrayResource(f));
    }
    @GetMapping("file/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable ObjectId id) {
        File file = fileService.findFileById(id);
        byte[] f = fileManagerService.getFile(file.getFileRef());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalFileName() + "\"")
                .body(new ByteArrayResource(f));
    }

    /*@GetMapping("defaultPicture") TODO*/

    @PostMapping("file")
    public String uploadFile(MultipartFile file, JwtAuthenticationToken token) {
        String ownerId = token.getName();
        return fileMapper.toOutput(fileService.upload(fileMapper.toFile(file, ownerId), file)).getContentUrl();
    }
}
