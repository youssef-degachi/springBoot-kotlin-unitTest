package com.example.thenewboston.controller

import com.example.thenewboston.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
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
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    var objectMapper: ObjectMapper
){

    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks`() {
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("abcdef") } // Check the first entry
                    jsonPath("$[1].accountNumber") { value("1234") }  // Check the second entry
                    jsonPath("$[2].accountNumber") { value("5678") }  // Check the third entry
                }
        }
    }

    @Nested
    @DisplayName("Get api/banks/{accountNumber}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBank {

        @Test
        fun `should return the bank with the given account number`() {
            // given
            val accountNumber = "1234" // Ensure this is a String

            // when // then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value(1.2) } // Use value directly
                    jsonPath("$.transactionFee") { value(5) } // Use value directly
                }
        }

        @Test
        fun `should return NOt Found if the account number does not exist`() {
            // given
            val accountNumber = "does_not_exist"

            // when// then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status{isNotFound()} }
        }
        }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class  PostNewBank{

        @Test
        fun `should add the new bank`() {
           // given
            val newBank = Bank("acc123", 31.415, 2)

            // when
            val performPost = mockMvc.post(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber"){value("acc123")}
                    jsonPath("$.trust"){value("31.415")}
                    jsonPath("$.transactionFee"){value("2")}
                }
        } // end test

        @Test
        fun `should retrun BAD REQUEST if bank wiht given account number already exists`() {
            /* TEXT */
            
            // given
            val invalidBank = Bank("1234",1.2,5)
            // when 
            val performPost = mockMvc.post(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        } //end test
    }

}
