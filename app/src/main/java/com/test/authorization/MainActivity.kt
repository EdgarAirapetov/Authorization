package com.test.authorization

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.authorization.ui.fragment.AuthorizationFragment
import com.test.authorization.ui.fragment.InfoFragment
import com.test.authorization.utils.PreferenceManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            val preferenceManager = PreferenceManager(this)

            val fragment = if (preferenceManager.isAuthorized()) InfoFragment() else AuthorizationFragment()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    fun showInfo() {
        val infoFragment = InfoFragment()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, infoFragment, null)
            .commit()
    }

    fun openAuthorization() {
        val authorizationFragment = AuthorizationFragment()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, authorizationFragment, null)
            .commit()
    }
}
