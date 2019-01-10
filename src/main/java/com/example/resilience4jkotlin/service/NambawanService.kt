package com.example.resilience4jkotlin.service

import com.example.resilience4jkotlin.client.NambawanServiceClient
import org.springframework.stereotype.Service

@Service
class NambawanService(
        val serviceClient: NambawanServiceClient
) {

    fun success(): String {
        return serviceClient.success()
    }
}