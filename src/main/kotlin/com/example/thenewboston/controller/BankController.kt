//3
package com.example.thenewboston.controller

import com.example.thenewboston.model.Bank
import com.example.thenewboston.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("/api/banks")
class BankController (private val service: BankService){


    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message,HttpStatus.NOT_FOUND)


    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message,HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getBanks():Collection<Bank> = service.getBanks()

    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber: String) = service.getBank(accountNumber)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody bank: Bank): Bank = service.addBank(bank)


    @PatchMapping
    fun updateBank(@RequestBody bank: Bank): Bank = service.updateBank(bank)


    @DeleteMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBank(@PathVariable accountNumber: String): Unit = service.deleteBank(accountNumber)


    @PutMapping("/{accountNumber},{transactionFee}")
    fun withDraw(@PathVariable accountNumber: String, @PathVariable transactionFee: Int): Bank = service.withDraw(accountNumber,transactionFee)

    @PutMapping("/{fromAccount}/{toAccount}/{amount}")
    fun transfer(@PathVariable fromAccount: String, @PathVariable toAccount: String, @PathVariable amount: Int): Bank = service.transfer(fromAccount,toAccount,amount)



}