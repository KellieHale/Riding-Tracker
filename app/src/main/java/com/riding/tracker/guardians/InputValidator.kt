package com.riding.tracker.guardians

import android.util.Patterns
import java.text.Normalizer
import java.util.regex.Pattern

class InputValidator {
    companion object {
        private const val phonePatterns = ("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$")
        private val pattern = Pattern.compile(phonePatterns)

        fun isValidPhoneNumber(phoneNumber: String) : Boolean {
            return pattern.matcher(phoneNumber).matches()
        }

        fun isNameValid(name: String): Boolean {
            val normalizedName = Normalizer
                .normalize(name, Normalizer.Form.NFD)
                .replace("\\p{Mn}".toRegex(), "")
            return normalizedName.matches(Regex("^[a-zA-Z ]*$"))
        }

        fun isEmailValid(emailAddress: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
        }
    }
}