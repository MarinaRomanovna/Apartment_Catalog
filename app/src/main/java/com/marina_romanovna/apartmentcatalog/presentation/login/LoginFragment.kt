package com.marina_romanovna.apartmentcatalog.presentation.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.marina_romanovna.apartmentcatalog.ApartmentApplication
import com.marina_romanovna.apartmentcatalog.R
import com.marina_romanovna.apartmentcatalog.databinding.LoginFragmentBinding
import com.marina_romanovna.apartmentcatalog.utils.observe
import com.marina_romanovna.apartmentcatalog.utils.showSnackbar
import com.marina_romanovna.apartmentcatalog.utils.states.LoginState
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.login_fragment) {

    companion object {
        fun newInstance() = LoginFragment()
    }

    @Inject
    lateinit var factory: LoginViewModel.Factory

    private var _binding: LoginFragmentBinding? = null
    private val binding: LoginFragmentBinding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel> { factory }

    override fun onAttach(context: Context) {
        ApartmentApplication.dagger.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = LoginFragmentBinding.bind(view)

        viewModel.state.observe(lifecycleScope) { loginState ->
            when (loginState) {
                is LoginState.Failure -> {
                    showSnackbar(binding.root, "LoginState.Failure")
                }
                is LoginState.Success -> {
                    viewModel.onAuthClick()
                }
                null -> {
                    showSnackbar(binding.root, "LoginState.Loading")
                }
            }
        }

        binding.btnEntry.setOnClickListener {
            val email = binding.etLogin.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.login(email, password)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}