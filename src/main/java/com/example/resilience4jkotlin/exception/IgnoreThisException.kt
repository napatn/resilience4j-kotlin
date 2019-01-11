package com.example.resilience4jkotlin.exception

class IgnoreThisException(
        override val message: String?
): RuntimeException()