package com.example.ecomdiploma.data.contactfrag

import com.example.ecomdiploma.domain.contactfrag.EmailRequest
import com.example.ecomdiploma.domain.contactfrag.EmailResponse
import com.example.ecomdiploma.domain.contactfrag.EmailService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailServiceImpl : EmailService {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.emailjs.com")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    private val emailJSApi: EmailJSApi = retrofit.create(EmailJSApi::class.java)

    override fun sendEmail(emailRequest: EmailRequest, onResult: (Boolean, String?) -> Unit) {
        emailJSApi.sendEmail(emailRequest).enqueue(object : Callback<EmailResponse> {
            override fun onResponse(call: Call<EmailResponse>, response: Response<EmailResponse>) {
                if (response.isSuccessful) {
                    onResult(true, response.body()?.message)
                } else {
                    onResult(false, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<EmailResponse>, t: Throwable) {
                onResult(false, t.message)
            }
        })
    }
}
