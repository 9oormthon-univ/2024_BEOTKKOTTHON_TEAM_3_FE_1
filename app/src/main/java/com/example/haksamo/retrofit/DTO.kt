package com.example.haksamo.retrofit

import com.google.gson.annotations.SerializedName

// 로그인 -------------------------------------------
data class UserDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class AuthnMailDto(
    @SerializedName("email") val email: String,
    @SerializedName("authnCode") val AuthnCode: String
)

data class TokenDto(
    @SerializedName("grantType") val grantType: String,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("accessTokenExpiresIn") val accessTokenExpiresIn: Long
)
// ------------------------------------------- 로그인

// 회원가입 -------------------------------------------

// ------------------------------------------- 회원가입


//검색 ----------------------------------

//------------------------------------- 검색