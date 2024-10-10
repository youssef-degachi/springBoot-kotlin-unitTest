package com.example.thenewboston.service

import com.example.thenewboston.datasource.BankDataSource
import com.example.thenewboston.model.Bank
import org.springframework.stereotype.Service


@Service
class BankService (private val dataSource: BankDataSource){
    fun getBanks(): Collection<Bank> {
        return dataSource.retrieveBanks()
    }

    fun getBank(accountNumber: String): Bank = dataSource.retrieveBanks(accountNumber)
    //fun getBank(accountNumber: String): Bank = dataSource.retrieveBanks(accountNumber )

}