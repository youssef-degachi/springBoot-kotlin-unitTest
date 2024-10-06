package com.example.thenewboston.datasource.Mock

import com.example.thenewboston.datasource.BankDataSource
import com.example.thenewboston.model.Bank
import org.springframework.stereotype.Repository


@Repository
class MockBankDataSource: BankDataSource {

    override fun getBanks(): Collection<Bank> {
        val banks = listOf(
            Bank("abc",0.1,1),
            Bank("1234",0.1,1),
            Bank("5678",0.1,1)

        )
        return banks
    }
}