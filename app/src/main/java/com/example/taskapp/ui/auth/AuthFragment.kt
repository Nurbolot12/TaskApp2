package com.example.taskapp.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentAuthBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class AuthFragment : Fragment() {
    lateinit var binding: FragmentAuthBinding
    var auth = FirebaseAuth.getInstance()
    var correctCode: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAuthBinding.inflate(layoutInflater)
        initListeners()
        return binding.root

    }

    private fun initListeners() {
        binding.etPhoneFrt.setText("+996")
        binding.btnSave.setOnClickListener {
            if (binding.etPhoneFrt.text!!.isNotEmpty()) {
                sendPhone()
                Toast.makeText(requireContext(), "отправка...", Toast.LENGTH_SHORT).show()
            } else {
                binding.etPhoneFrt.error = "Введите номер телефона"
            }
        }
        binding.btnConfirn.setOnClickListener {
            sendCode()
        }
    }

    private fun sendPhone() {
        auth.setLanguageCode("en")
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(binding.etPhoneFrt.text.toString())       // Phone number to verify binding.etPhone.text.stribg
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show()
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    Toast.makeText(requireContext(), exception.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onCodeSent(
                    vertificetionCode: String,
                    p1: PhoneAuthProvider.ForceResendingToken
                ) {
                    correctCode = vertificetionCode
                    binding.etPhoneLayoutFrt.isVisible = false
                    binding.btnSave.isVisible = false

                    binding.etCodeLayout.isVisible = true
                    binding.btnConfirn.isVisible = true
                    super.onCodeSent(vertificetionCode, p1)
                }

            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun sendCode() {
        val credential =
            PhoneAuthProvider.getCredential(
                correctCode.toString(), binding.etCode.text.toString()
            )
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)

            .addOnCompleteListener(requireActivity()) { task ->
                Log.w("ololo", "signinWithCradential:failure", task.exception)
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.navigation_home)
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(
                            requireContext(),
                            task.exception.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }
}