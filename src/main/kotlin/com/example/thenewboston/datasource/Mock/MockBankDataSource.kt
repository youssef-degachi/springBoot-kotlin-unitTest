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


<<<<<<< HEAD
    override fun retrieveBank(accountNumber: String): Bank =
        banks.first {it.accountNumber == accountNumber}


=======
    override fun retrieveBanks(accountNumber: String): Bank =
        banks.first {it.accountNumber = accountNumber}

}
>>>>>>> e157923 (create nested test class , but in MockBankDataSource  the retrieveBanks(account) not work)


}



