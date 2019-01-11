package com.example.resilience4jkotlin.predicate

import com.example.resilience4jkotlin.exception.IgnoreThisException
import java.util.function.Predicate

class NambawanCircuitBreakerPredicate: Predicate<Throwable> {
    override fun test(t: Throwable): Boolean {
        return t !is IgnoreThisException
    }
}