package com.test.authorization.utils

const val HTTPS_PREFIX = "https://"
const val SEPARATOR = "/"
const val DOT = "."
const val DOMAIN = ".retailcrm.ru"

fun formatMainUrl(url: String) : String? {
    var finalUrl = url.trim()
    if (finalUrl.isEmpty())
        return null

    if (finalUrl.startsWith(HTTPS_PREFIX))
        finalUrl = finalUrl.replace(HTTPS_PREFIX, "")

    if (finalUrl.endsWith(SEPARATOR))
        finalUrl = finalUrl.replace(SEPARATOR, "")

    val splitArray = finalUrl.split(DOT)

    if (splitArray.size != 3)
        return null

    if (!finalUrl.substring(0, finalUrl.length).endsWith(DOMAIN))
        return null

        return finalUrl
}

fun isValidEmail(target: CharSequence): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
}