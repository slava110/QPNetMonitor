package com.slava_110.qpnetmonitor.model.dto

sealed class AuthResponse {
    class Success(val displayName: String, val admin: Boolean): AuthResponse()
    class Failure(val error: String): AuthResponse()
}