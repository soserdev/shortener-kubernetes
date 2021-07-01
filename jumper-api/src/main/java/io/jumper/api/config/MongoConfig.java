package io.jumper.api.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConfigurationProperties(prefix = "io.jumper.api.mongodb", ignoreUnknownFields = true)
@Setter
@Slf4j
public class MongoConfig extends AbstractMongoClientConfiguration {

    private String host;
    private String database;

    @Bean
    public MongoClient mongo() {
        log.info("Connecting to mongodb: '" + host + "'...");
        ConnectionString connectionString = new ConnectionString(host);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        log.info("Using mongodb database: '" + database + "'");
        return new MongoTemplate(mongo(), database);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}

