package com.example.resilience4jkotlin.service

import com.example.resilience4jkotlin.client.NambawanServiceClient
import org.springframework.stereotype.Service

@Service
class NambawanService(
        val serviceClient: NambawanServiceClient
) {

    fun method1(): String {
        return serviceClient.myRNG()
    }

    fun circuitbreaker(): String {
        return serviceClient.getDataWithCircuitBreaker()
    }
}