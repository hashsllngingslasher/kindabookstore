package com.example.kindabookstore.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kindabookstore.R
import com.example.kindabookstore.databinding.FragmentContactBinding
import com.example.kindabookstore.ui.login.LoginFragment
import com.example.kindabookstore.ui.login.SignInFragment
import com.google.firebase.auth.FirebaseAuth

class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signOutButton.setOnClickListener {
            signOut()
        }
        binding.check.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser == null) {
                Toast.makeText(requireContext(), "не зареган", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(requireContext(), "ЗАРЕГАН", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        // Возвращаемся на экран входа или регистрации
        findNavController().navigate(R.id.loginFragment)
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.container, LoginFragment())
//            .commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}