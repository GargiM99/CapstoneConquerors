package ca.ttms.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

import ca.ttms.repositories.UserRepo;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AzureStorageConfig {

    @Value("${spring.cloud.azure.storage.connection-string}")
    private String connectionString;

    @Bean
    public BlobServiceClient blobServiceClient() {
        return new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
    }
}
