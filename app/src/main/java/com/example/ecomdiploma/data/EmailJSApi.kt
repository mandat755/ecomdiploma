package com.example.ecomdiploma.data.remote

import com.example.ecomdiploma.domain.model.EmailRequest
import com.example.ecomdiploma.domain.model.EmailResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailJSApi {
    @POST("https://api.emailjs.com/api/v1.0/email/send")
    fun sendEmail(@Body emailRequest: EmailRequest): Call<EmailResponse>
}
