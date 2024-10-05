package com.example.thenewboston.datasource

import com.example.thenewboston.model.Bank

interface BankDataSource {
    fun getBanks(): Collection<Bank>
}