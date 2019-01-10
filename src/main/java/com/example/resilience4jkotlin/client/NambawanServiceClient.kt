package com.example.resilience4jkotlin.client

import org.springframework.stereotype.Service

@Service
class NambawanServiceClient() {

    fun success(): String {
        return "success"
    }
}