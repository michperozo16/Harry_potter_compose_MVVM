package com.mich.mylogin

import java.util.regex.Pattern

private const val EMAIL_VALIDATOR_REGEX = "^(.+)@(.+)\$"

class EmailState :TextFieldState(validator = ::isEmailValid, errorFor = ::emailValidationError)

private fun isEmailValid(email: String) = Pattern.matches(EMAIL_VALIDATOR_REGEX, email)

private fun emailValidationError(email: String) = "Invalid Email: $email"