package student.projects.beatsyncprototype

import com.google.gson.annotations.SerializedName

//Data for music sessions
data class Session(
    val id: String,
    val title: String,
    val artist: String,
    @SerializedName("imageUrl") val imageUrl: String
)
