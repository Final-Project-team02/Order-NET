package bitc.fullstack.app.appserver

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AppServerInterface {

  @GET("server")
  fun serverTest(): Call<String>

}