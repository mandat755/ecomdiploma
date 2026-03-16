package com.example.ecomdiploma.data.contactfrag

import com.example.ecomdiploma.domain.contactfrag.EmailRequest
import com.example.ecomdiploma.domain.contactfrag.EmailResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailJSApi {
    @POST("https://api.emailjs.com/api/v1.0/email/send")
    fun sendEmail(@Body emailRequest: EmailRequest): Call<EmailResponse>
}
