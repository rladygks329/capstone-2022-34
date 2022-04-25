package com.capstone.momomeal.feature

import java.time.LocalDateTime
import java.util.*

data class User(
    val name: String = "",
    val idUser: Int = 0,
    val email: String ="",
    val profileImgUrl: String = "",
    val totalRate: Int = 50,
    val listReview: List<Int> = listOf()
)

data class Chatroom (
    val nameRoom: String = "",
    val idChatroom: Int = 0,
    val category: Category = Category.Chicken,
    val maxCapacity: Int = 4,
    val namePickupPlace: String ="",
    val coordPickupPlaceX: Double = 0.0,
    val coordPickupPlaceY: Double = 0.0,
    val listUid: List<Int> = listOf()
)

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

enum class Category {
    Chicken, Pizza, Korean, Chinese, Japanese,
    Western, Snackbar, MidnightSnack, BoiledPork, CafeAndDesert,
    Fastfood,
}

val fakeUser = User("김요한", 0, "rladygks329@naver.com", "https://miro.medium.com/max/1400/0*EhfyHg8fBGUEyAE-.png", 50, listOf(1,2,3))

val fakeUsers = listOf(
    User("김미나", 1, "mina123@naver.com", "https://miro.medium.com/max/1400/0*EhfyHg8fBGUEyAE-.png", 50, listOf(1,2,3)),
    User("조자현", 2, "ssibo12@gmail.com", "https://miro.medium.com/max/1400/0*EhfyHg8fBGUEyAE-.png", 40, listOf(1,2,3)),
    User("유비", 3, "poskei@nate.com", "https://miro.medium.com/max/1400/0*EhfyHg8fBGUEyAE-.png", 60, listOf(1,2,3))
)

val fakeChatrooms = listOf(
    Chatroom("Bhc 뿌링클 뿌개실분 ~ ", 123, Category.Chicken, 3, "국민대학교 정문", 3.3, 1.1, listOf(7, 49, 89)),
    Chatroom("밤 12시에 족발 먹을 사람 있니?", 128, Category.BoiledPork, 3, "서울대입구 4번출구", 3.9, 1.1, listOf(3, 29, 69))
)