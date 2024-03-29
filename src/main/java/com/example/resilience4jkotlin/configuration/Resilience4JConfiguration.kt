package com.example.resilience4jkotlin.configuration

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerProperties
import io.github.resilience4j.prometheus.CircuitBreakerExports
import io.prometheus.client.CollectorRegistry
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Resilience4JConfiguration {

    @Bean
    fun registry(circuitBreakers: List<CircuitBreaker>, circuitBreakerRegistry: CircuitBreakerRegistry)
            : CollectorRegistry {
        val collectorRegistry = CollectorRegistry.defaultRegistry
        collectorRegistry.register(CircuitBreakerExports.ofCircuitBreakerRegistry(circuitBreakerRegistry))
        return collectorRegistry
    }

    @Bean
    @Qualifier("nambawanCircuitBreaker")
    fun nambawanCircuitBreaker(circuitBreakerRegistry: CircuitBreakerRegistry,
                               circuitBreakerProperties: CircuitBreakerProperties): CircuitBreaker {
        val config = circuitBreakerProperties.createCircuitBreakerConfig("nambawan")
        return circuitBreakerRegistry.circuitBreaker("nambawan", config)
    }

//    fun nambawanCircuitBreaker2(circuitBreakerRegistry: CircuitBreakerRegistry): CircuitBreaker {
//        val config = CircuitBreakerConfig.custom()
//                .failureRateThreshold(50f)
//                .waitDurationInOpenState(Duration.ofMillis(1000))
//                .ringBufferSizeInHalfOpenState(2)
//                .ringBufferSizeInClosedState(2)
//                .build()
//
//        return circuitBreakerRegistry.circuitBreaker("nambawan", config)
//    }
//
//    fun nambawanCircuitBreaker3(circuitBreakerRegistry: CircuitBreakerRegistry): CircuitBreaker {
//        val config = CircuitBreakerConfig.ofDefaults()
//        return circuitBreakerRegistry.circuitBreaker("nambawan", config)
//    }
//
//
//    fun nambawanCircuitBreaker9(circuitBreakerRegistry: CircuitBreakerRegistry): CircuitBreaker {
//        return circuitBreakerRegistry.circuitBreaker("nambawan")
//    }
}