package com.example.resilience4jkotlin.exception

class BusinessLogicException(
        override val message: String?
): RuntimeException()