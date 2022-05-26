package com.capstone.momomeal.feature

import com.capstone.momomeal.api.MomomealAPI
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.dto.LoginForm


class LoginRepository(
    private val momomeal: MomomealAPI = MomomealService.momomealAPI
) {
    fun login(email: String, password: String) = momomeal.login(LoginForm(email, password))
}