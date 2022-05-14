package com.capstone.momomeal.data

import android.graphics.Bitmap
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class User(
    val name: String = "",
    val idUser: Int = 0,
    val email: String ="",
    val profileImgUrl: String = "",
    val totalRate: Int = 50,
    val listReview: List<Int> = listOf()
) : Parcelable{
    fun trans_User_light() : User_light{
        return User_light(name, idUser, profileImgUrl)
    }
}

@Parcelize
data class User_light(
    @SerializedName("name") var name: String = "",
    @SerializedName("userId") var idUser: Int = 0,
    @SerializedName("img") var profileImgUrl: String = "",
) : Parcelable {
    constructor(user: User) : this() {
        this.name = user.name
        this.idUser = user.idUser
        this.profileImgUrl = user.profileImgUrl
    }
}

// ChatActivity쪽에서만 사용되는 데이터 클래스. id -> membInfo로 연결되는 HashMap에 사용됨.
data class membInfo(
    var name: String = "",
    var bitmap: Bitmap
)

@Parcelize
data class Chatroom (
    val idChatroom: Long = 0,
    val category: Category = Category.Chicken,
    val nameRoom: String = "",
    val maxCapacity: Int? = 4,
    val nameStore: String = "",
    val namePickupPlace: String ="",
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val coordPickupPlaceX: Double? = 0.0,
    val coordPickupPlaceY: Double? = 0.0,
    val listUid: List<Int>? = listOf(),
) : Parcelable
/*
"id": 33,
"category": "WESTERN",
"title": "파스타 먹을 사람",
"hostId": 3,----
"maxCapacity": 4,
"storeName": "빠네 파스타 전문점",
"pickupPlaceName": "스파오티",
"distance": 400843,
"createdDate": "2022-05-12T18:43:28.660192"
}
]
* */
data class Review (
    val idReview: Int = 0,
    val rateSign: Rate,
    val conReview: String = "",
    val dateReview: LocalDateTime,
)

data class Chat (
    val uid: Int = 0,
    val chatContent: String = "",
)

enum class Rate {
    Good, Bad,
}

enum class Category(val KoreanName: String) {
    Chicken("치킨"),
    Pizza("피자"),
    Korean("한식"),
    Chinese("중식"),
    Japanese("일식"),
    Western("양식"),
    Snackbar("분식"),
    MidnightSnack("야식"),
    BoiledPork("족발/보쌈"),
    CafeAndDesert("카페/디저트"),
    Fastfood("패스트푸드")
}



data class LoginResponse(
    @SerializedName("check") val check : Int
)

val fakeUser = User("김요한", 9, "rladygks329@naver.com", "https://miro.medium.com/max/1400/0*EhfyHg8fBGUEyAE-.png", 50, listOf(1,2,3))

val fakeUsers = listOf(
    User("김미나", 1, "mina123@naver.com", "https://miro.medium.com/max/1400/0*EhfyHg8fBGUEyAE-.png", 50, listOf(1,2,3)),
    User("조자현", 2, "ssibo12@gmail.com", "https://miro.medium.com/max/1400/0*EhfyHg8fBGUEyAE-.png", 40, listOf(1,2,3)),
    User("유비", 3, "poskei@nate.com", "https://miro.medium.com/max/1400/0*EhfyHg8fBGUEyAE-.png", 60, listOf(1,2,3))
)
