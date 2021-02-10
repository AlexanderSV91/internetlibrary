package com.faceit.example.internetlibrary.configuration;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@RequiredArgsConstructor
@EnableElasticsearchRepositories(basePackages = "com.faceit.example.internetlibrary.repository")
@ComponentScan(basePackages = {"com.faceit.example.internetlibrary.service"})
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    private final ElasticsearchRestClientProperties clientProperties;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration.TerminalClientConfigurationBuilder builder =
                ClientConfiguration.builder()
                        .connectedTo(clientProperties.getUris().get(0))
                        .withConnectTimeout(clientProperties.getConnectionTimeout())
                        .withSocketTimeout(clientProperties.getReadTimeout())
                        .withBasicAuth(clientProperties.getUsername(), clientProperties.getPassword());
        final ClientConfiguration clientConfiguration = builder.build();
        return RestClients.create(clientConfiguration).rest();
    }
}
