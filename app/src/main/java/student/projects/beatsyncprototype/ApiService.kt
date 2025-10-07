package student.projects.beatsyncprototype

import retrofit2.http.GET
import student.projects.beatsyncprototype.Session

//Fetches data from the API
interface ApiService {
    @GET("sessions")
    suspend fun getSessions(): List<Session>
}