package ca.ttms.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobStorageException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlobService {
	
	@Autowired
    private BlobServiceClient blobServiceClient;
	
	public void uploadJsonBlob(String containerName, String blobName, String jsonContent) throws BlobStorageException {
        BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName);
        blobClient.upload(
            new ByteArrayInputStream(jsonContent.getBytes(StandardCharsets.UTF_8)),
            jsonContent.length(),
            true
        );
    }
	
	public void uploadJsonBlob(String containerName, String blobName, Object jsonObject) throws BlobStorageException {
	    try {
	        BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName);
	        Gson gson = new Gson();
	        String jsonContent = gson.toJson(jsonObject);
	        blobClient.upload(
	            new ByteArrayInputStream(jsonContent.getBytes(StandardCharsets.UTF_8)),
	            jsonContent.length(),
	            true
	        );
	    } catch (Exception ex) {
	        // If an error occurs during the upload, throw a custom BlobStorageException
	        throw new BlobStorageException("Blob couldn't upload", null, ex);
	    }
	}

	
	public String downloadJsonBlob(String containerName, String blobName) throws BlobStorageException {
	    BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName);
	    String jsonContent = blobClient.downloadContent().toString();
	    return jsonContent;
	}
	
	public Object downloadJsonBlob(String containerName, String blobName, TypeToken typeToken ) throws BlobStorageException {
	    BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName);
	    String jsonContent = blobClient.downloadContent().toString();
	    
	    Gson gson = new Gson();
	    Object jsonObject = gson.fromJson(jsonContent, typeToken.getType());
	    return jsonObject;
	}
}
