package com.example.thenewboston.datasource.Mock

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MockBankDataSourceTest {
    private val  mockDataSource = MockBankDataSource()

    @Test
    fun `should provide a Collection of banks`() {
        // when
        val banks = mockDataSource.retrieveBanks()
        // then
        //assertThat(banks).isNotEmpty
        assertThat(banks.size).isGreaterThanOrEqualTo(3)

    }



    @Test
    fun `should provide some mock data` (){
        // when
        val banks = mockDataSource.retrieveBanks()

        // then
        assertThat(banks).allMatch{it.accountNumber.isNotBlank()}
        assertThat(banks).anyMatch {it.trust != 0.0}
        assertThat(banks).anyMatch{it.transactionFee != 0}

}

    
}