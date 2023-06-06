package com.riding.tracker.login

import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED
    }

//    val authenticationState = FirebaseUserLiveData.map { user ->
//        if (user != null) {
//            AuthenticationState.AUTHENTICATED
//        } else {
//            AuthenticationState.UNAUTHENTICATED
//        }
//    }


}

