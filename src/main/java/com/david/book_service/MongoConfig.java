package com.david.book_service;
/*
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Slf4j
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${MONGODB_USERNAME}")
    private String username;

    @Value("${MONGODB_PASSWORD}")
    private String password;

    @Override
    public @NonNull MongoClient mongoClient() {
        String uri = "mongodb+srv://" + username + ":" + password +"@webshop.hy2s4mk.mongodb.net";
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

 */
