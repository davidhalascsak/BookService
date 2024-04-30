package com.david.book_service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Base64;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${mongodb.username}")
    private String username;

    @Value("${mongodb.password}")
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
