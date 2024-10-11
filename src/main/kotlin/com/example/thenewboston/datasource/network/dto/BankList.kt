package tv.codealong.tutorials.springboot.thenewboston.datasource.network.dto

import com.example.thenewboston.model.Bank

data class BankList(
    val results: Collection<Bank>
)