package com.test.authorization.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.authorization.MainActivity
import com.test.authorization.R
import com.test.authorization.api.entity.request.LoginRequest
import com.test.authorization.common.gone
import com.test.authorization.common.visible
import com.test.authorization.databinding.FragmentAuthorizationBinding
import com.test.authorization.utils.PreferenceManager
import com.test.authorization.utils.formatMainUrl
import com.test.authorization.utils.isValidEmail
import com.test.authorization.viewModel.AuthorizationViewModel

class AuthorizationFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(AuthorizationViewModel::class.java)
    }

    private lateinit var binding: FragmentAuthorizationBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_authorization, container, false)

        context?.let {
            preferenceManager = PreferenceManager(it)
        }

        binding.apply {

            loginButton.setOnClickListener {

                    val mainUrl = formatMainUrl(domainCrmInput.text.toString())
                    if (mainUrl.isNullOrEmpty()) {
                        showError(R.string.set_correct_address)
                        return@setOnClickListener
                    }

                    val login = loginInput.text.toString()

                    if (login.isEmpty()) {
                        showError(R.string.input_login)
                        return@setOnClickListener
                    }

                    if (!isValidEmail(login)) {
                        showError(R.string.set_correct_email)
                        return@setOnClickListener
                    }

                    val password = passwordInput.text.toString()

                    if (password.isEmpty()) {
                        showError(R.string.input_password)
                        return@setOnClickListener
                    }

                    val loginRequest = LoginRequest()
                    loginRequest.username = login
                    loginRequest.password = password

                    preferenceManager.domain = mainUrl
                    viewModel.login(loginRequest)

                    isLoading = true
            }

            tryAgainButton.setOnClickListener {
                loginLayout.visible()
                errorLayout.gone()
            }
        }

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: AuthorizationViewModel) {
        viewModel.apply {
            loginLiveData.observe(viewLifecycleOwner, Observer { response ->

                preferenceManager.accessToken = response.accessToken?: ""
                preferenceManager.refreshToken = response.refreshToken?: ""

                if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) && activity is MainActivity) {
                    (activity as MainActivity).showInfo()
                }
            })

            errorLiveData.observe(viewLifecycleOwner, Observer {error ->
                binding.apply {
                    isLoading = false
                    loginLayout.gone()
                    errorLayout.visible()
                    errorTv.text = error
                }


            })
        }
    }

    private fun showError(resId: Int) {
        context?.let {
            Toast.makeText(it, it.resources.getString(resId), Toast.LENGTH_LONG).show()
        }
    }
}