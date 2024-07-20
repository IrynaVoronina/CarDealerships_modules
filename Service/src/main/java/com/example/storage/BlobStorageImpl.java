package com.example.storage;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BlobStorageImpl implements BlobStorage {

    private final BlobServiceClient blobServiceClient;
    private final BlobContainerClient containerClient;

    public BlobStorageImpl(@Value("${azure.myblob.url}") String connectionString,
                           @Value("${azure.myblob.container}") String containerName) {
        this.blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
        this.containerClient = blobServiceClient.getBlobContainerClient(containerName);
    }

    @Override
    public List<String> listBlobs() {
        List<String> files = new ArrayList<>();
        containerClient.listBlobs().forEach(blobItem -> files.add(blobItem.getName()));
        return files;
    }

    @Override
    public BlobClient getBlobClient(String blobName) {
        return containerClient.getBlobClient(blobName);
    }

    @Override
    public boolean blobExists(String blobName) {
        return getBlobClient(blobName).exists();
    }

    @Override
    public void uploadBlob(String blobName, byte[] data) {
        BlobClient blobClient = getBlobClient(blobName);
        blobClient.upload(BinaryData.fromBytes(data), true);
    }

    @Override
    public void deleteBlob(String blobName) {
        BlobClient blobClient = getBlobClient(blobName);
        if (blobClient.exists()) {
            blobClient.delete();
        }
    }
}
