package com.david.book_service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Base64;

@Slf4j
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${MONGODB_USERNAME}")
    private String username;

    @Value("${mongodb.password}")
    private String password;

    @Override
    public @NonNull MongoClient mongoClient() {
        String decodedUsername = new String(Base64.getDecoder().decode(username));
        String decodedPassword = new String(Base64.getDecoder().decode(password));

        log.info("encoded username: {}", username);
        log.info("decoded username: {}", decodedUsername);

        String uri = "mongodb+srv://" + decodedUsername + ":" + decodedPassword +"@webshop.hy2s4mk.mongodb.net";
        ConnectionString connectionString = new ConnectionString(uri);

        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected @NonNull String getDatabaseName() {
        return "shopdb";
    }
}
