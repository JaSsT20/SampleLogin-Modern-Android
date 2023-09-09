package com.levid.chapter3_login.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.levid.chapter3_login.util.MutableSavedState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.levid.chapter3_login.data.AuthenticationState
import com.levid.chapter3_login.util.*
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

import kotlinx.coroutines.plus


@HiltViewModel
class LoginViewModel @Inject constructor(
    dispatchers: SampleLoginDispatchers,
    stateHandle: SavedStateHandle
): ViewModel() {
    private val username = MutableSavedState(
        stateHandle,
        key = "UserName",
        defValue = ""
    )
    private val password = MutableSavedState(
        stateHandle,
        "password",
        defValue = ""
    )

    private val passwordVisibility = MutableSavedState(
        stateHandle,
        "password_key",
        defValue = false
    )
    fun userNameChanged(userName: String) {
        username.value = userName
    }

    fun passwordChanged(updatedPassword: String) {
        password.value = updatedPassword
    }

    fun passwordVisibility(visibility: Boolean) {
        passwordVisibility.value = visibility
    }

    fun login() {
        loadingProgress.start()
    }
    private val loadingProgress = ProgressLoader()

    val state = combine(
        username.flow,
        password.flow,
        passwordVisibility.flow,
        loadingProgress.flow
    ) { username, password, passwordToggle, isLoading ->
        AuthenticationState(
            userName = username,
            password = password,
            togglePasswordVisibility = passwordToggle,
            loading = isLoading
        )
    }.stateIn(
        coroutineScope = viewModelScope + dispatchers.main,
        initialValue = AuthenticationState.EMPTY_STATE
    )
}

