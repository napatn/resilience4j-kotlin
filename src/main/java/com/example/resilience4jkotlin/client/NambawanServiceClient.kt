package com.example.resilience4jkotlin.client

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.vavr.control.Try
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class NambawanServiceClient(
        @Qualifier("nambawanCircuitBreaker")
        val circuitBreaker: CircuitBreaker
) {

    fun getDataWithCircuitBreaker(): String {
        val function = CircuitBreaker.decorateSupplier(circuitBreaker, { this.failed() })
        return Try.ofSupplier(function).get()
    }

    fun success(): String {
        return "success"
    }

    fun failed(): String {
        throw Exception("an error")
    }
}