package com.example.resilience4jkotlin.client

import com.example.resilience4jkotlin.exception.IgnoreThisException
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
        val function = CircuitBreaker.decorateSupplier(circuitBreaker, { this.myRNG() })
        return Try.ofSupplier(function).get()
    }

    fun myRNG(): String {
        val rng = Math.random() * 10 + 1
        if (rng < 4) {
            return failed()
        } else if (rng < 8) {
            return ignoredException()
        } else {
            return success()
        }

    }

    fun success(): String {
        return "success"
    }

    fun failed(): String {
        throw Exception("an error, this should trip the circuit open")
    }

    fun ignoredException(): String {
        throw IgnoreThisException("an ignored error, this shouldn't change the circuit state")
    }
}