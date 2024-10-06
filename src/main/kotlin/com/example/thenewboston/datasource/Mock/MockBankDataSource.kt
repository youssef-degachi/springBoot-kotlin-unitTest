package com.example.thenewboston.datasource.Mock

import com.example.thenewboston.datasource.BankDataSource
import com.example.thenewboston.model.Bank
import org.springframework.stereotype.Repository


@Repository
class MockBankDataSource: BankDataSource {

    override fun getBanks(): Collection<Bank> {
        TODO("Not yet implemented")
    }
}