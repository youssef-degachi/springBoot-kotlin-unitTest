package com.example.thenewboston

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/hello")
class HelloWorldController {

        @GetMapping()
        fun helloWorld(): String {
            return "Hellow, this is a rest endpoint";
        }
        @GetMapping("youssef")
         fun helloYoussef(): String =  "Hello youssef"
}