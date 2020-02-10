package com.test.authorization.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.test.authorization.MainActivity
import com.test.authorization.R
import com.test.authorization.databinding.FragmentInfoBinding
import com.test.authorization.utils.PreferenceManager

class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)

        context?.let {
            preferenceManager = PreferenceManager(it)

            binding.apply {

                accessTokenTv.text = preferenceManager.accessToken

                closeSessionButton.setOnClickListener {
                    preferenceManager.clearAllData()

                    if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) && activity is MainActivity) {
                        (activity as MainActivity).openAuthorization()
                    }
                }
            }
        }

        return binding.root

    }
}