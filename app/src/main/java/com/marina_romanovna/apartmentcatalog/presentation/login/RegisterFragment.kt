package com.marina_romanovna.apartmentcatalog.presentation.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.marina_romanovna.apartmentcatalog.R
import com.marina_romanovna.apartmentcatalog.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment(R.layout.register_fragment) {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private var _binding: RegisterFragmentBinding? = null
    private val binding: RegisterFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = RegisterFragmentBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}