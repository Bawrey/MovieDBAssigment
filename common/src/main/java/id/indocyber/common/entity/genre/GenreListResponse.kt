package id.indocyber.common.entity.genre


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreListResponse(
    @SerializedName("genres")
    val genres: List<Genre>
):Parcelable