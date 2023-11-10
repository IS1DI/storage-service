package com.is1di.storage.repository;

import com.is1di.storage.entity.files.File;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<File, ObjectId> {
}
