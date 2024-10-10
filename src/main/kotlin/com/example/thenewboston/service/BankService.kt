package com.example.thenewboston.service

import com.example.thenewboston.datasource.BankDataSource
import com.example.thenewboston.model.Bank
import org.springframework.stereotype.Service


@Service
class BankService (private val dataSource: BankDataSource){
<<<<<<< HEAD

    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()

    fun getBank(accountNumber: String): Bank = dataSource.retrieveBank(accountNumber)
=======
    fun getBanks(): Collection<Bank> {
        return dataSource.retrieveBanks()
    }

    fun getBank(accountNumber: String): Bank = dataSource.retrieveBanks(accountNumber)
>>>>>>> e157923 (create nested test class , but in MockBankDataSource  the retrieveBanks(account) not work)
    //fun getBank(accountNumber: String): Bank = dataSource.retrieveBanks(accountNumber )

}