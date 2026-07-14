package me.yassu.bookstore.order.client;

import me.yassu.bookstore.order.ApplicationProperties;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
class CatalogServiceClientConfig {

    @Bean
    RestClient restClient(RestClient.Builder builder, ApplicationProperties applicationProperties) {
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactoryBuilder.simple()
                .withCustomizer(customizer -> {
                    customizer.setConnectTimeout(Duration.ofSeconds(5));
                    customizer.setReadTimeout(Duration.ofSeconds(5));
                })
                .build();
        return builder.baseUrl(applicationProperties.catalogServiceUrl())
                .requestFactory(requestFactory)
                .build();
    }
}
