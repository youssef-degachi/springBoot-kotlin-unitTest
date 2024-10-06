package com.example.thenewboston.datasource.Mock

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MockBankDataSourceTest {

    private val  mockDataSource = MockBankDataSource()

    @Test
fun `shouild provide a Collection of banks`() {

    // when
    val banks = mockDataSource.getBanks()

    // then
    Assertions.assertThat(banks).isNotEmpty

    }
}