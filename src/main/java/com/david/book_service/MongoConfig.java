package com.david.book_service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Base64;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${MONGODB_USERNAME}")
    private String username;

    @Value("${MONGODB_PASSWORD}")
    private String password;

    @Override
    public MongoClient mongoClient() {
        String decodedUsername = new String(Base64.getDecoder().decode(username));
        String decodedPassword = new String(Base64.getDecoder().decode(password));

        String uri = "mongodb+srv://" + decodedUsername + ":" + decodedPassword +"@webshop.hy2s4mk.mongodb.net";
        return MongoClients.create(uri);
    }

    @Override
    protected String getDatabaseName() {
        return "shopdb";
    }
}
