package com.example.resilience4jkotlin.controller

import com.example.resilience4jkotlin.service.NambawanService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/namba1")
class NambawanController(
        val service: NambawanService
) {

    @GetMapping("/method1")
    fun method1(): String {
        return service.method1()
    }

    @GetMapping("/all")
    fun all(): String {
        return service.all()
    }

    @GetMapping("/circuitbreaker")
    fun circuitbreaker(): String {
        return service.circuitbreaker()
    }

    @GetMapping("/ratelimiter")
    fun ratelimiter(): String {
        return service.ratelimiter()
    }
}