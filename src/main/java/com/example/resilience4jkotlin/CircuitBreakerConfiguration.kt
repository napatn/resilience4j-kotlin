package com.example.resilience4jkotlin

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerProperties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CircuitBreakerConfiguration {

    @Bean
    fun defaultCircuitBreakerRegistry(): CircuitBreakerRegistry {
        return CircuitBreakerRegistry.ofDefaults()
    }

    @Bean
    @Qualifier("nambawanCircuitBreaker")
    fun nambawanCircuitBreaker(defaultCircuitBreakerRegistry: CircuitBreakerRegistry,
                               circuitBreakerProperties: CircuitBreakerProperties): CircuitBreaker {
        val config = circuitBreakerProperties.createCircuitBreakerConfig("nambawan")
        return defaultCircuitBreakerRegistry.circuitBreaker("nambawan", config);
    }
}