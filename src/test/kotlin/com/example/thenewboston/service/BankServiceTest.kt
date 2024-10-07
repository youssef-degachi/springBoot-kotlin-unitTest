package com.example.thenewboston.service

import com.example.thenewboston.datasource.BankDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BankServiceTest{

    private val dataSource: BankDataSource = mockk()
    private val banksService = BankService(dataSource)

    @Test
    fun `should call its data source to retrieve banks` (){
        // given
        every { dataSource.retrieveBanks() } returns emptyList()

        // when
        val banks = banksService.getBanks()
        
        // then
        verify (exactly = 1){ dataSource.retrieveBanks() }
    }
}