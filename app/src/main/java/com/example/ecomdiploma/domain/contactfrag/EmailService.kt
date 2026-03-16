package com.example.ecomdiploma.domain.contactfrag

interface EmailService {
    fun sendEmail(emailRequest: EmailRequest, onResult: (Boolean, String?) -> Unit)
}
