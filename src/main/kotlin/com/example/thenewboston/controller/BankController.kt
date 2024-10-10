package com.example.thenewboston.controller

import com.example.thenewboston.model.Bank
import com.example.thenewboston.service.BankService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("api/banks")
class BankController (private val service: BankService){


    @GetMapping()
    fun getBanks():Collection<Bank> = service.getBanks()
    //fun getBanks(): String = "Works"

    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber: String) = service.getBank(accountNumber)
    //fun getBank(@PathVariable accountNumber: String) =" service.getBanks(accountNumber)"

}