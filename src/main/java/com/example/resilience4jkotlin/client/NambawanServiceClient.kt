package com.example.resilience4jkotlin.client

import com.example.resilience4jkotlin.exception.IgnoreThisException
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.ratelimiter.RateLimiter
import io.vavr.control.Try
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class NambawanServiceClient(
        @Qualifier("nambawanCircuitBreaker")
        val circuitBreaker: CircuitBreaker,

        @Qualifier("nambawanRateLimiter")
        val rateLimiter: RateLimiter
) {

    fun all(): String {
        var supplier = { this.myRNG() }
//        var function = RateLimiter.decorateSupplier(rateLimiter, supplier)
//        function = CircuitBreaker.decorateSupplier(circuitBreaker, function)
        var function = CircuitBreaker.decorateSupplier(circuitBreaker, supplier)
        function = RateLimiter.decorateSupplier(rateLimiter, function)

        return Try.ofSupplier(function)
                .recover(this::recover)
                .get()
    }

    fun getDataWithCircuitBreaker(): String {
        val function = CircuitBreaker.decorateSupplier(circuitBreaker, { this.myRNG() })
        return Try.ofSupplier(function)
                .recover(this::recover)
                .get()
    }

    fun getDataWithRateLimiter(): String {
        val function = RateLimiter.decorateSupplier(rateLimiter, { this.myRNG() })
        return Try.ofSupplier(function)
                .recover(this::recover)
                .get()
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

    fun recover(t: Throwable): String {
        if(t is IgnoreThisException) {
            circuitBreaker.onSuccess(0)
        }

        throw t
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