package com.example.ecomdiploma.presentation.fragments.aboutfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ecomdiploma.R
import com.example.ecomdiploma.databinding.FragmentAboutInfoBinding
import com.example.ecomdiploma.presentation.viewmodel.AuthorizationViewModel


class AboutInfoFragment : Fragment() {

    private var _binding: FragmentAboutInfoBinding? = null
    private val binding get() = _binding!!

    private val authorizationViewModel: AuthorizationViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button1.setOnClickListener {
            findNavController().navigate(R.id.action_aboutInfoFragment_to_MainFragment)
            authorizationViewModel.invalidateToolbar(88)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}