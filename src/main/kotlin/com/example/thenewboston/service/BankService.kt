//1

package com.example.thenewboston.service

import com.example.thenewboston.datasource.BankDataSource
import com.example.thenewboston.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service


@Service
class BankService (@Qualifier("mock") private val dataSource: BankDataSource){

    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()

    fun getBank(accountNumber: String): Bank = dataSource.retrieveBank(accountNumber)

    fun addBank(bank: Bank): Bank = dataSource.createBank(bank)

    fun updateBank(bank: Bank): Bank = dataSource.updateBank(bank)
    //fun getBank(accountNumber: String): Bank = dataSource.retrieveBanks(accountNumber )
    fun deleteBank(accountNumber: String): Unit = dataSource.deleteBank(accountNumber)

    // user service

    fun withDraw(accountNumber: String, transactionFee: Number): Bank = dataSource.withDraw(accountNumber, transactionFee)

}