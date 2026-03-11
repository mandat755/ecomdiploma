package com.example.ecomdiploma.domain.service

import com.example.ecomdiploma.domain.model.EmailRequest

interface EmailService {
    fun sendEmail(emailRequest: EmailRequest, onResult: (Boolean, String?) -> Unit)
}
