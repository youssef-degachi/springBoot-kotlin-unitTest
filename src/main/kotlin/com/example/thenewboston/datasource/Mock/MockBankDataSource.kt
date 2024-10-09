package com.example.thenewboston.datasource.Mock

import com.example.thenewboston.datasource.BankDataSource
import com.example.thenewboston.model.Bank
import org.springframework.stereotype.Repository


@Repository
class MockBankDataSource: BankDataSource {


    val banks = listOf(
        Bank("abcdef",0.1,1),
        Bank("1234",1.2,5),
        Bank("5678",0.1,1)

    )
    override fun retrieveBanks(): Collection<Bank> = banks


}