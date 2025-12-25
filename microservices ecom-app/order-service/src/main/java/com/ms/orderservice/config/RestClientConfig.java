package com.ms.orderservice.config;


import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {


    @Bean
    @Primary
    public RestClient.Builder restClientBuilder(ObservationRegistry observationRegistry) {
        return RestClient.builder().observationRegistry(observationRegistry);
    }


    @LoadBalanced
    @Bean
    public RestClient.Builder loadbalancedClientBuilder(ObservationRegistry observationRegistry) {
        return RestClient.builder().observationRegistry(observationRegistry);
    }

}
