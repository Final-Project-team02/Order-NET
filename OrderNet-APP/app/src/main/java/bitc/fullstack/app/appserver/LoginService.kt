package bitc.fullstack.app.appserver

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequestDto(
    val userId: String,
    val userPw: String,
    val userType: String
)

data class LoginResponseDto(
    val token: String,
    val userType: String,
    val userRefId: String,
    val branchSupervisor: String?,
    val warehouseName: String?
)