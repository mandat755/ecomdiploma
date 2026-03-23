package com.example.ecomdiploma.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthorizationViewModel : ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> get() = _user

    private val _toolbar = MutableLiveData<Int?>()
    val toolbar: LiveData<Int?> get() = _toolbar

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        _user.value = mAuth.currentUser
    }

    fun signInWithGoogle(credential: AuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.value = mAuth.currentUser
                } else {
                    _user.value = null
                }
            }
    }
    fun checkCurrentUser(): FirebaseUser? {
        return mAuth.currentUser
    }

    fun signOut() {
        mAuth.signOut()
        _user.value = null
    }

    fun invalidateToolbar(num: Int) {
        _toolbar.value = num
    }
}
