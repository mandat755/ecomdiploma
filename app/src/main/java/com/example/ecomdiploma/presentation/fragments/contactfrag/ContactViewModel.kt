package com.example.ecomdiploma.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecomdiploma.domain.contactfrag.EmailService
import com.example.ecomdiploma.domain.contactfrag.EmailRequest
import com.example.ecomdiploma.data.contactfrag.EmailServiceImpl

class ContactViewModel : ViewModel() {

    private val _emailStatus = MutableLiveData<String>()
    val emailStatus: LiveData<String> get() = _emailStatus

    private val emailService: EmailService = EmailServiceImpl()

    fun sendContactEmail(name: String, email: String, subject: String, message: String) {
        if (name.isNotEmpty() && email.isNotEmpty() && subject.isNotEmpty() && message.isNotEmpty()) {
            val params = mapOf(
                "from_name" to name,
                "from_email" to email,
                "subject" to subject,
                "message" to message
            )

            val emailRequest = EmailRequest(
                service_id = "service_dfmswvi",
                template_id = "template_a61yawd",
                user_id = "gGdlvfzba0Y2IVO0C",
                template_params = params
            )

            emailService.sendEmail(emailRequest) { success, responseMessage ->
                if (success) {
                    _emailStatus.postValue("Email sent successfully: $responseMessage")
                } else {
                    _emailStatus.postValue("Failed to send email: $responseMessage")
                }
            }
        } else {
            _emailStatus.postValue("Please fill all fields.")
        }
    }
}
