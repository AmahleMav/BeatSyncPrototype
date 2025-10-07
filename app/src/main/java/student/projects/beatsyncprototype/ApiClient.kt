package student.projects.beatsyncprototype

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// API client for Retrofit
object ApiClient {

    // MockAPI URL
    private const val BASE_URL = "https://68e552cd21dd31f22cc16898.mockapi.io/ap1/v1/"

    val service: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
