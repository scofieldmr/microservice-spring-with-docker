package com.ms.orderservice.config;

import com.ms.orderservice.client.product.ProductClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class ClientConfig {

    @Bean
    @Primary
    public RestClient.Builder plainRestClientBuilder() {
        return RestClient.builder();
    }

    // ✅ ONLY for service-to-service calls
    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public ProductClient productClient(@Qualifier("loadBalancedRestClientBuilder") RestClient.Builder restClientBuilder){
        RestClient restClient = restClientBuilder.baseUrl("http://product-service")
                                .defaultStatusHandler(HttpStatusCode::is4xxClientError,
                                       ((request, response) -> Optional.empty()))
                                 .build();
        RestClientAdapter restClientAdapter =RestClientAdapter.create(restClient);
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(ProductClient.class);
    }
}
