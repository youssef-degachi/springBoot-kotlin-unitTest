package com.example.thenewboston.datasource.Mock

import com.example.thenewboston.datasource.BankDataSource
import com.example.thenewboston.model.Bank
import org.springframework.stereotype.Repository


@Repository("mock")
class MockBankDataSource: BankDataSource {


    val banks = mutableListOf(
        Bank("abcdef",0.1,5000),
        Bank("1234",1.2,9000),
        Bank("5678",0.1,1200)
    )
    /*
    * function for user account
    * */

    // return all banks
    override fun retrieveBanks(): Collection<Bank> = banks

    // return one bank
    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull() {it.accountNumber == accountNumber}
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")
    // create bank
    override fun createBank(bank: Bank): Bank {
        if (banks.any {it.accountNumber == bank.accountNumber}){
            throw  IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists.")
        }
        banks.add(bank)
        return bank
    }

    // update bank
    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull() {it.accountNumber == bank.accountNumber}
            ?: throw NoSuchElementException("Could not find a bank with account number ${bank.accountNumber}")

        banks.remove(currentBank)
        banks.add(bank)
        return bank
    }

    // delete bank
    override fun deleteBank(accountNumber: String) {
        val currentBank = banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")
        banks.remove(currentBank)
    }

    /*
    * function for user service
    * */

    override fun withDraw(accountNumber: String,transactionFee: Number): Bank {
        val accountBalance = 20000;
        val currentBank = banks.firstOrNull() {it.accountNumber == accountNumber}
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")
        if (transactionFee.toDouble() > accountBalance) {
            throw IllegalArgumentException("The amount you want is bigger than the balance in the account")
        }
        if (currentBank.transactionFee < transactionFee.toDouble()){
            throw IllegalArgumentException("The amount you want is bigger than the what you have")
        }
        currentBank.transactionFee -= transactionFee.toInt()
        println("Withdrawal successful. Amount withdrawn: $transactionFee")

        return currentBank
    }
}



