package com.app.majuapp.screen.login

import androidx.lifecycle.ViewModel
import com.app.majuapp.util.SocialLoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SocialLoginViewModel @Inject constructor() : ViewModel() {

    private val _socialLoginUiState = MutableStateFlow<SocialLoginUiState>(SocialLoginUiState.Idle)
    val socialLoginUiState = _socialLoginUiState.asStateFlow()

    fun kakaoLogin() {
        _socialLoginUiState.value = SocialLoginUiState.SocialLogin
    }

    fun kakaoLoginSuccess() {
        _socialLoginUiState.value = SocialLoginUiState.LoginSuccess
    }

    fun kakaoSocialAppLoginFail() {
        _socialLoginUiState.value = SocialLoginUiState.SocialAppLoginFail
    }

    fun kakaoLoginFail() {
        _socialLoginUiState.value = SocialLoginUiState.LoginFail
    }

    fun loginIdle() {
        _socialLoginUiState.value = SocialLoginUiState.Idle
    }

}