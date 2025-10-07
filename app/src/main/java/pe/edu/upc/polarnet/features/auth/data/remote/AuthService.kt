package pe.edu.upc.polarnet.features.auth.data.remote

import pe.edu.upc.polarnet.features.auth.data.models.LoginRequestDto
import pe.edu.upc.polarnet.features.auth.data.models.LoginResponseDto
import pe.edu.upc.polarnet.features.auth.data.models.UserDetailDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(@Body loginRequestDto: LoginRequestDto): Response<LoginResponseDto>

    @GET("auth/me")
    suspend fun getCurrentUser(
        @Header("Authorization") authorization: String
    ): Response<UserDetailDto>
}