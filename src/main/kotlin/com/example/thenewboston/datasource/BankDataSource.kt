package com.example.thenewboston.datasource

import com.example.thenewboston.model.Bank

interface BankDataSource {
    fun retrieveBanks(): Collection<Bank>
<<<<<<< HEAD
    fun retrieveBank(accountNumber: String): Bank
=======
    fun retrieveBanks(accountNumber: String): Bank
>>>>>>> e157923 (create nested test class , but in MockBankDataSource  the retrieveBanks(account) not work)
}