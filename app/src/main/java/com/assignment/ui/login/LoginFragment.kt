package com.assignment.ui.login

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
import com.assignment.ui.home.HomeFragment

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
//        binding.tvTitle.text = requireActivity().getString(R.string.login)
        binding.btLoginSignup.text = requireActivity().getString(R.string.login)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.observer.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

                viewModel.observer.value = null

            }
        })

        viewModel.observerSuccess.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if(it){
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.frame, HomeFragment())
                    transaction.commit()
                }

            }
        })

        viewModel.observerData.observe(viewLifecycleOwner, Observer {
                if (it){
                    if (viewModel.isLogin) {
                        binding.tvTitle.text = "SignUp"
                        binding.btLoginSignup.text = "SignUp"
                        binding.textView.text = "Click here to Login"

                    } else {
                        binding.tvTitle.text = "Login"
                        binding.btLoginSignup.text = "Login"
                        binding.textView.text = "Click here to Signup"
                    }
                    viewModel.isLogin = !viewModel.isLogin
                    viewModel.observerData.value = false
                }
        })

    }


}
