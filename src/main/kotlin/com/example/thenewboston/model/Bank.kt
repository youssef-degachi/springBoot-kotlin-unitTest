package com.example.thenewboston.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Bank(
    @JsonProperty("account_number")
    val accountNumber: String,

    @JsonProperty("trust")
    val trust: Double,

    @JsonProperty("default_transaction_fee")
    var transactionFee: Int
)