package com.example.thenewboston.datasource.Mock

import com.example.thenewboston.datasource.BankDataSource
import com.example.thenewboston.model.Bank
import org.springframework.stereotype.Repository


@Repository
class MockBankDataSource: BankDataSource {


    val banks = mutableListOf(
        Bank("abcdef",0.1,1),
        Bank("1234",1.2,5),
        Bank("5678",0.1,1)
    )
    override fun retrieveBanks(): Collection<Bank> = banks


    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull() {it.accountNumber == accountNumber}
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")

    override fun createBank(bank: Bank): Bank {
        if (banks.any {it.accountNumber == bank.accountNumber}){
            throw  IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists.")
        }
        banks.add(bank)
        return bank
    }
}



