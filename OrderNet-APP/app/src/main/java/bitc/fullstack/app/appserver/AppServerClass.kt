package bitc.fullstack.app.appserver

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object AppServerClass {

  private val BASE_URL = "http://175.200.235.27:8080/app/"

  val instance: AppServerInterface by lazy {
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(ScalarsConverterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(AppServerInterface::class.java)
  }
}














