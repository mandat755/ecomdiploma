package com.example.ecomdiploma.presentation.fragments

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.ecomdiploma.R
import com.example.ecomdiploma.databinding.FragmentAuthorizationBinding
import com.example.ecomdiploma.presentation.viewmodel.AuthorizationViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider

class AuthorizationFragment : Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    private val authorizationViewModel: AuthorizationViewModel by activityViewModels()

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val themedContext = ContextThemeWrapper(requireContext(), com.google.android.material.R.style.Theme_Material3_Light_NoActionBar)  // Заміни на твій стиль теми

        val themedInflater = LayoutInflater.from(themedContext)

        _binding = FragmentAuthorizationBinding.inflate(themedInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //перевірка, чи користувач вже авторизований
        authorizationViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                findNavController().navigate(R.id.action_authorizationFragment_to_MainFragment)
                authorizationViewModel.invalidateToolbar(1)
            }
        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) //токен отриманий у Firebase Console
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        binding.googleSignInButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.result
            firebaseAuthWithGoogle(account!!)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Auth failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        authorizationViewModel.signInWithGoogle(credential)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}