package com.example.ecomdiploma.presentation.fragments.contactfrag

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ecomdiploma.databinding.FragmentContactBinding
import com.example.ecomdiploma.presentation.viewmodel.ContactViewModel

class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    private val contactViewModel: ContactViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val email = binding.emailField.text.toString().trim()
            val subject = binding.subject.text.toString().trim()
            val message = binding.message.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || subject.isEmpty() || message.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all the fields.", Toast.LENGTH_SHORT).show()
            } else {
                contactViewModel.sendContactEmail(name, email, subject, message)
            }
        }

        contactViewModel.emailStatus.observe(viewLifecycleOwner, { status ->
            Toast.makeText(requireContext(), "Sent.", Toast.LENGTH_SHORT).show()
            Log.d("MyEmailJSLOG", "$status")
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
