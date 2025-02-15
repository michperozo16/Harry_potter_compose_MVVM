package com.mich.mylogin

class PasswordState
    :TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError)

private fun isPasswordValid(password: String) = password.length > 4
@Suppress( "UNUSED_PARAMETER")
private fun passwordValidationError(password: String) = "InValid Password"