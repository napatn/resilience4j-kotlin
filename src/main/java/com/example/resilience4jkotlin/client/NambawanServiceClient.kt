package com.example.resilience4jkotlin.client

import com.example.resilience4jkotlin.exception.BusinessLogicException
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException
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
        return Try.ofSupplier(function)
                .recover(this::recover)
                .get()
    }

    fun recover(t: Throwable): String {
        if(t is BusinessLogicException) {
            circuitBreaker.onSuccess(0)
            return "business logic error"
        } else if (t is CircuitBreakerOpenException) {
            return "circuit breaker nambawan is open"
        }

        System.out.println("ERROR!")
        return "error"
    }

    fun myRNG(): String {
        val rng = Math.random() * 10 + 1
        if (rng < 4) {
            return error()
        } else if (rng < 8) {
            return businessLogicError()
        } else {
            return success()
        }
    }

    fun success(): String {
        return "success"
    }

    fun error(): String {
        throw Exception("an error")
    }

    fun businessLogicError(): String {
        throw BusinessLogicException("an error caused by business logic")
    }
}