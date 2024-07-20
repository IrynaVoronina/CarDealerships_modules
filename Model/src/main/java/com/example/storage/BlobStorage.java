package com.example.storage;

import com.azure.storage.blob.BlobClient;

import java.util.List;

public interface BlobStorage {

    List<String> listBlobs();

    BlobClient getBlobClient(String blobName);

    boolean blobExists(String blobName);

    void uploadBlob(String blobName, byte[] data);

    void deleteBlob(String blobName);
}
