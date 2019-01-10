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

    @GetMapping("/success")
    fun success(): String {
        return service.success()
    }
}