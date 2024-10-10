package com.example.thenewboston.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc


    @Nested
    @DisplayName("getBanks()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks`() {
            mockMvc.get("/api/banks")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("1234") }
                }
        }
    }


    @Nested
    @DisplayName("getBank()")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBank {

        @Test
        fun `should return the bank with the given account number` (){
            //description
            /*  text  */
            // given
            val accountNumber = 1234
            // when// then
            mockMvc.get("/api/banks/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status{ isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust"){value{1.2}}
                    jsonPath("$.transactionFee"){value{5}}
                }
        }
    }

}
