package com.nisum.azure.configs;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureBlobStorageConfig {

  @Value("${azure.storage.account}")
  private String storageAccount;

  @Value("${azure.storage.key}")
  private String storageKey;


  @Bean
  public BlobServiceClient blobServiceClient() {
    return new BlobServiceClientBuilder()
        .connectionString("DefaultEndpointsProtocol=https;AccountName="+storageAccount+";AccountKey="+storageKey+";EndpointSuffix=core.windows.net")
        .buildClient();
  }


}
