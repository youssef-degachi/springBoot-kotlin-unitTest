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
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    var objectMapper: ObjectMapper
){

    val baseUrl = "/api/banks"

    // get all banks
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
                    jsonPath("$[0].account_number") { value("abcdef") } // Check the first entry
                    jsonPath("$[1].account_number") { value("1234") }  // Check the second entry
                    jsonPath("$[2].account_number") { value("5678") }  // Check the third entry
                }
        }
    }

    // get one bank
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
                    jsonPath("$.default_transaction_fee") { value(1000) } // Use value directly
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

    // add new bank
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
                    content{
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))
                    }
                }

            mockMvc.get("$baseUrl/${newBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(newBank)) } }
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
    
    // update one bank
    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PatchExistingBank  {
        @Test
        fun `should update an existing bank`() {

            // given
            val updatedBank = Bank("1234",1.2,5)

            // when
            val performPatchRequest = mockMvc.patch(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }

            // then
            performPatchRequest
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }

            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                .andExpect { content{json(objectMapper.writeValueAsString(updatedBank))} }
        } //end test

        @Test
        fun `should return BAD REQUEST if no bank with given account number exists`() {
            /* TEXT */
            
            // given
            val invalidBank = Bank("does_not_exist", 1.0, 1)
            // when 
            val performPatchRequest = mockMvc.patch(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            // then
            performPatchRequest
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        } //end test

    } // end nested



    // delete one bank
    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class DeleteExistingBank {

        @Test
        @DirtiesContext
        fun `should delete the bank with the given account number`() {
            // given
            val accountNumber = 1234

            // when/then
            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }

            mockMvc.get("$baseUrl/$accountNumber")
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return NOT FOUND if no bank with given account number exists`() {
            // given
            val invalidAccountNumber = "does_not_exist"

            // when/then
            mockMvc.delete("$baseUrl/$invalidAccountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }


    // withdraw from bank
    @Nested
    @DisplayName("Update /api/banks/withdraw")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class AbelToWithDrawOrNot {

        @Test
        fun `should return error when account does not exist`() {
            // given
            val nonExistentAccountNumber = "9999"
            val amount = 1000
            val expectedErrorMessage = "Account not found"

            // when
            mockMvc.get("$baseUrl/$nonExistentAccountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    jsonPath("$.message").value(expectedErrorMessage)
                }
        }

        @Test
        fun `should return error when withdrawal amount exceeds account balance`() {
            // given
            val accountBalance = 20000
            val amount = 10000
            val accountNumber = "1234"
            val expectedErrorMessage = "The amount you want is bigger than the balance in the account"

            // when
            mockMvc.put("$baseUrl/$accountNumber,$amount")
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    jsonPath("$.message").value(expectedErrorMessage)
                }
        } // end test

        @Test
        fun `should allow withdrawal when amount is less than or equal to account balance`() {
            // given
            val amount = 1000
            val accountNumber = "1234"

            // when
            mockMvc.put("$baseUrl/$accountNumber,$amount")
                .andDo { print() }
                .andExpect {
                    status { isOk() } // Assuming successful withdrawal returns 204
                }
        } // end test

    }


    // transferee amount  of money
    @Nested
    @DisplayName("Put /api/banks/transfer")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class TransferAmountToOtherBank {
     @Test
     fun `should return error when there one account not exist `() {
         // given
         val fromAccount = "1234";
         val toAccount = "9999";
         val amount = 1000;
         val expectedErrorMessage = "Account not found";
         // when/then
         mockMvc.put("$baseUrl/$fromAccount/$toAccount/$amount")
             .andDo { print() }
             .andExpect {
                 status{ isNotFound() }
                 jsonPath("$.message").value(expectedErrorMessage)
             }
     } // end test


      @Test
      fun `should return error when account transectionFee is not enough`() {
          // given
          val fromAccount = "abcdef";
          val toAccount = "1234";
          val amount = 1000000000
          // when
          mockMvc.put("$baseUrl/$fromAccount/$toAccount/$amount"){
              contentType = MediaType.APPLICATION_JSON
          }
              .andDo { print() }
              .andExpect {
                  status { isBadRequest() }
          }
          // then

      } // end test
    } // end nested


}
