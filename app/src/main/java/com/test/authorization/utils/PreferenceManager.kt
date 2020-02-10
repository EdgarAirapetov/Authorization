package com.test.authorization.utils

import android.content.Context

const val ACCESS_TOKEN = "ACCESS_TOKEN"
const val REFRESH_TOKEN = "REFRESH_TOKEN"
const val DOMAIN_CRM = "DOMAIN_CRM"
const val DEFAULT_DOMAIN = "https://android-test.retailcrm.ru/"

class PreferenceManager (context: Context) {

    private val PRIVATE_MODE = 0
    private val pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    private val editor = pref.edit()

    var accessToken: String
        get() {
            return pref.getString(ACCESS_TOKEN, "").toString()
        }
        set(accessToken) {
            editor.putString(ACCESS_TOKEN, accessToken)
            editor.commit()
        }

    var refreshToken: String
        get() {
            return pref.getString(REFRESH_TOKEN, "").toString()
        }
        set(refreshToken) {
            editor.putString(REFRESH_TOKEN, refreshToken)
            editor.commit()
        }

    var domain: String
        get() {
            return pref.getString(DOMAIN_CRM, DEFAULT_DOMAIN).toString()
        }
        set(domain) {
            editor.putString(DOMAIN_CRM, domain)
            editor.commit()
        }

    fun clearAllData() {
        accessToken = ""
        refreshToken = ""
        domain = ""
    }

    fun isAuthorized(): Boolean {
        return accessToken.isNotEmpty()
    }

    companion object {
        const val PREF_NAME = "retailCRM_TEST"
    }

}