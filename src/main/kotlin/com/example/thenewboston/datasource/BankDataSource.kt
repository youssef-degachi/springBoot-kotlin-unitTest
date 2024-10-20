//2
package com.example.thenewboston.datasource

import com.example.thenewboston.model.Bank

interface BankDataSource {
    fun retrieveBanks(): Collection<Bank>

    fun retrieveBank(accountNumber: String): Bank

    fun createBank(bank: Bank): Bank

    fun updateBank(bank: Bank): Bank

    fun deleteBank(accountNumber: String)

    fun withDraw(accountNumber: String, amount: Number): Bank

    fun transfer(fromAccount: String, toAccount: String, amount: Number): Bank
    //fun transfer(string: String, number: Number): Bank

}