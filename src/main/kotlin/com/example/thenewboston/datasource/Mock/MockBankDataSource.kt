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
        if(transactionFee.toDouble() < 0){
            throw IllegalArgumentException("the amount can't be negative")
        }
        currentBank.transactionFee -= transactionFee.toInt()
        println("Withdrawal successful. Amount withdrawn: $transactionFee")

        return currentBank
    }

    override fun transfer(fromAccount: String, toAccount: String, amount: Number,): Bank {
        val accountBalance = 20000;
        val currentBank = banks.firstOrNull() {it.accountNumber == fromAccount}
            ?: throw NoSuchElementException("Could not find bank with account number $fromAccount")

        val nextBank = banks.firstOrNull() {it.accountNumber == toAccount}
            ?: throw NoSuchElementException("We can't find bank with account number $toAccount")


        if(amount.toDouble() > currentBank.transactionFee){
            throw IllegalArgumentException("the amount you will transfer is bigger than what you have")
        }

        if(amount.toDouble() < 0){
            throw IllegalArgumentException("the amount can't be negative")
        }

        if(amount.toDouble() > accountBalance){
            throw IllegalArgumentException("The amount you will transfer is bigger than the balance in the account the max transfer is $accountBalance")
        }

        nextBank.transactionFee += amount.toInt()
        currentBank.transactionFee -= amount.toInt()
        println("transfer successful. Amount transfer: $amount")
        return currentBank

    }
}



