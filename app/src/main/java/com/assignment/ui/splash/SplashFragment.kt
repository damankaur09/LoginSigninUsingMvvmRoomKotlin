package com.assignment.ui.splash

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.assignment.R
import com.assignment.databinding.LoginFragmentBinding
import com.assignment.databinding.SplashFragmentBinding
import com.assignment.ui.login.LoginFragment

class SplashFragment : Fragment() {

    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: SplashFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.splash_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.observer.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame, LoginFragment())
                transaction.commit()
                viewModel.observer.value = null

            }
        })
    }

}
