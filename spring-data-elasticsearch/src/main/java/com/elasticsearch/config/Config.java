package com.elasticsearch.config;

import java.net.InetSocketAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.elasticsearch.repository")
@ComponentScan(basePackages = { "com.elasticsearch.service" })
public class Config {

    private static Logger logger = LoggerFactory.getLogger(Config.class);
    
    @Bean
    public Client transportClient() {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "elasticsearch-sumanth").build();
        Client client = TransportClient.builder().settings(settings).build();
        addTransportClient(client, "localhost", 9300);
        return client;
    }

    private TransportClient addTransportClient(Client client, String host, int port) {
        return ((TransportClient) client).addTransportAddress(
                new InetSocketTransportAddress(new InetSocketAddress(host, port)));
    }


    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(transportClient());
    }
}
