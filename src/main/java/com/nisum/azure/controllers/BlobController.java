package com.nisum.azure.controllers;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BlobController {

  private final BlobServiceClient blobServiceClient;

  public BlobController(BlobServiceClient blobServiceClient) {
    this.blobServiceClient = blobServiceClient;
  }

  @PostMapping("/create-blob")
  public ResponseEntity<String> createBlob(@RequestParam("file") MultipartFile file)
      throws IOException {

    String containerName = "createtestcontainer";
    BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

    String blobName = file.getOriginalFilename();
    BlobClient blobClient = containerClient.getBlobClient(blobName);

    blobClient.upload(file.getInputStream(), file.getSize());

    return new ResponseEntity<>("Blob file created successfully", HttpStatus.OK);
  }


  @GetMapping("/fetch-blob")
  public ResponseEntity<byte[]> fetchBlob(@RequestParam("blobName") String blobName) {
    String containerName = "createtestcontainer";
    BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);


    BlobClient blobClient = containerClient.getBlobClient(blobName);
    ByteArrayOutputStream blobOutputStream = new ByteArrayOutputStream();
    blobClient.download(blobOutputStream);
    byte[] content = blobOutputStream.toByteArray();

    return new ResponseEntity<>(content, HttpStatus.OK);
  }


}
