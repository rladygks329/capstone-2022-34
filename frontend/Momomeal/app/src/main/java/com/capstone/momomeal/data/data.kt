package com.capstone.momomeal.data

import android.graphics.Bitmap
import android.os.Parcelable
import com.capstone.momomeal.data.dto.LoginResponse
import com.capstone.momomeal.data.dto.Member
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
    val listReview: List<Int> = listOf(),
    var x : Double = 0.0,
    var y : Double = 0.0,
    var address: String = ""
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



data class checkResponse(
    @SerializedName("check") val check : Int
)

val fakeUser = User("김요한", 9, "rladygks329@naver.com", "https://miro.medium.com/max/1400/0*EhfyHg8fBGUEyAE-.png", 50, listOf(1,2,3))



val choco1 : String = "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"
val choco2 = "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"
val choco3 : String = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMSEhUTExIVFRUXGBcVFRcYFRUVFRYVFRcXF" +
        "xUVFRUYHSggGBolHRUVITEhJSkrLi4uFx8zODMtNygtLisBCgoKDg0OGhAQGy0fICUtLS0tLS0tLS0tLS0tLS0tLS0tLS" +
        "0tLS0tLS0tLS0tLS0tLS0rLS0tLS0tLS0tLS0tLf/AABEIAMIBAwMBIgACEQEDEQH/xAAcAAABBQEBAQAAAAAAAA" +
        "AAAAAEAAIDBQYBBwj/xABDEAABAwEGAggDBgQEBQUAAAABAAIDEQQFEiExQVFhBhMiMnGBkaGxwdEUQlJykvAV" +
        "I2LhB1OCwiSistLxMzRDg5P/xAAZAQADAQEBAAAAAAAAAAAAAAACAwQBAAX/xAAkEQACAgICAgIDAQEAAAAAAAAA" +
        "AQIRAyESMUFRBDITFCJhcf/aAAwDAQACEQMRAD8AyK6ApGtTqJwsjATgnAKzsLWb0WNpGqLfRXNidwKdTktlYmxO" +
        "FMlJbLkY8VACzkjXBoxBairBHuu3nYXRHPRQwWkALm9GJbLd9rw5BPgtVVSicEoxjlNOSZdjhKPgvWyCia54cFWtt" +
        "NBmg4rfVxFUpSpj5R5RYPb4g15pvmhwKoi3uq4KKN+EF3DTx2V3g8qt0F3ZPG2bDIGltCHFxpQ+OyvrVY2FoLaEHS" +
        "mfw2XnN6TE0YO87N3IbfVazofb2hvUucezp/SPop5Sb6K4xivAW65i7RQm6XxHFstTC/qz2s2nRwz8+aJtD2PFAQa" +
        "6IE3F7Y6ShNaRkv4+GihKrrTfjTuFU9NrL1bw4bmhWabOQm8F2iRTa0byyXmK6p143pUUCx9ktRVgHFyF42x0Myhs" +
        "mY6pqpFG1tFK0J8I8VRNOblK2dYwk0CvLt6P9YKkoGxspmruz24tGSyU6NhDkR2rovhFQSqO02V0ZoQtlY7diyKZe" +
        "1jDhop38iuyn9VNGIK6ibVZsJUFFVGSkrRHKLi6ZwLhCdRIogSOiS6kuOJAU+NtShRIp7PMMSw5D7ezA2qqWW411V" +
        "vfUgLFkjPQqTI2z0sCVG8uK0kkLa2SXJeV3NeNCM1trFewDa1WwegcqH9J8JFNygLouEP1VLet9dZMGg5VWzuN+QTL" +
        "b0SvWyCbom3bJD/wgsyK2UcqBvZwAScmNUPw/IldMyV42PsGixMdqc15B4r0CScOBCwF9NwykhLwRt7H/Jm4x0GPtd" +
        "SuzTgMxHQZn5evzVJZXmR4btqeQGqOtzsbxGO63N/jsPIfFWTeqRDjju2DRkisru845D4BWFzXn9lfipUnveeoQJfU" +
        "4vutyb48UM51Ut/4PXs9Uum8mStrC5ufeidk0n+ndp9kWWNe7s1a8asOTh9RzC8ks1pdG4OY4tI3BW1uTpfHLSO1Chr" +
        "2ZBkQTvXZcnemY1W0GdIrmM7Ne0DUcDyJ28Vj5blwGjmlp5/LivTxIQATSWPZ7QKgcXAfEeijtV2MlaaUc2nmOYRpt" +
        "CpRTPOorvaEQ1gC0UvR+gyNVU2q63szotWRXRjwyqwZoUoYECZ6J8drTBRZCYAUXBaclW2iUcU1s6mnZVio0V3WqhVv" +
        "NbMllbFOKoq1XgANVFk6PSxq2OvF4cq4hObNiTHPV/xk1jVnmfMaeV0KqRTV0KglO0SXUlxxUvnUTrXRcvGwyROLXggo" +
        "ez2cucELYSVsvLNC6dtNkZZehQdqSibueI2gLQ3baaqdtWWRi4ozFp6DSMFYyVTzTSQ1ZJUH4r2SyzilCs7036ONniLm" +
        "DtDMIlFC5ZX5PI4bT/NrzW9u2+cIGa8vmjex5BFCDQqwsV5ObqiqhLdnr1kvvmoL5vcYdVgYL8XZ7wdJkKlDJOQUHxdm" +
        "ghtvZOaoLwGNxKlZG5uTgR4pzmZGmu3iihBRQWXI8j0CQtELC+nadk0fD6+Sgc2gDAe07Nx5HWqlklxEvPdZk3mdyPE" +
        "oaRxAJPedryGwQt+Q0vAyeQVoNBkoVwriAMdVcK4kAuOL3o5f81nPZdVm7TmPLgnXp0rmc1zQ7AHZkNFKDgOCp3Owjw" +
        "9yq2Z5caD981itnSpf9PQ/8M7e6UuikeSNWVNS11dAeC3FuYQ0tdGHf1A0y4+K8i6L2lkMzHPrgGR2y4+K9csttOHE1wn" +
        "hI1Gb2/mH3h7pkUnoCUmtnlnSiB8EtCOy7tMPEcOR5KjdayvbbwuuK0xOAo9jgRlSo4EV3HqvPr46EOZ2oS57QO000xgj" +
        "gB3vClUd12Jcb2jKNt53Tvt/NTG6jwSFyErnGzozo7FeVN0RDI6QpQXHxVrZrIGaIFgXkd+1JKkPiZhCVFM2IlWEFyuIq" +
        "ck7UUTU5Mq6JBWk1103Q77ueNM0KyxfkN4JpXQIkpDA7gVxGKo1vS+7mSMxUzC84tDhGV6De94gxnNeZ34c6hTQlyiy2U" +
        "OE0y1s9uBWjui2DivOrJOtJddpA3QJDJSs9D+0dmoR113gJAWlZez20EUR11to6tU1E0jMdObgAl6xo11WZbdNV6J0snD" +
        "hRUl2QguFdE3xbE+aRB0f6DumILsmrfXd0OghoQ2p4p92WtooBor5k4IQc0MeNrsz/TG64BZzI/sluTaalx0aF5bbJCaM" +
        "GrvZu/rotl/iPegdK2MHsxCp5vf8w2n6ivObxEzf5g7jtxnTkeBQ34Diq2ESuBNPuM93ISR9TUquFqdx8kTFOHcihYaok" +
        "K4nUSqsCOBSRCme+31TGt9Ep5KfvZYzetkFqm4eX1TbNFudT7BRxjEa7D90RgFAi6QvtnSVYXRfU1mdiieRxb90+IVauoR" +
        "h6ncHSWG0uFHCzznWv/pyHmP2VpXStc7BM3q5NGuHdd+V2h/KV4QCth0d6YSxtMUzeuiA0dmRwoUanXYt4/RsL7sMbaG0N" +
        "AzoJG78ssweRqFkQBtpt4KS231JK1rX5NFaNrUgbAk5migY8JsPYjI90SAroTKKeBormjboBb0WF3xtb2ijZry2CqrRKK" +
        "ZKH7RXRRZ8mj0PjYlZaRvDzmVe3bYg4LK2Nxc7gtrc76UqpMErlsuzrjDQc24oyK0SVgybJdV9o8p2eBWy9jgpVU1ptGI" +
        "JkgJURiK7DDjHYXycinLQO15BR9mttEHJCVGI3cEbiKUzTWS96bq7st+03WEhicVbWeFwpWtT3QBVzvALUq7BlK+i/tV4" +
        "GQ5pgtZBDWgknQAVJPIBPs91FgD56sYdGNI61/HDwHOgHMq2k6UMs7SyyQMgJyL645j4vIy8AulJNUzYKSdpBMNhtTWh0h" +
        "jszdQ6Z1HEcoxmh7XeLO6bTNOf6f5MftmR40WYtd4Pe4ukcXE8TUk86pRSHDipm44W/VKi1f8AKHSUn9mK0Pq417rczzcirBC" +
        "ZWEgYTnpnUHPNu4VbORkzZubjxPBcsV7SROq05cDoibpnJWiK8boacxRjv+R3/aqOeB0Zo8EfvUHcL0Gz26G0Vr2HnUHQnz1" +
        "+KDvC6S0ULat4HT/Sdj+81vYFUY6K0ka5j3Rcbw7ROtlzEGsdTxae8PD8Sron4XZ1BQtBRl7LNxp+9SgZ3kmg1U08mSbZodz" +
        "qfYLIrybJ3olijAH71Tik4ri5sJKhLoSXQhCOtbwV1dVkBIBcABmTti29EJdEAc/MkChFQKkHagV5DZHRMAORJz5oXJXsLi2" +
        "tdj7RdslKij+YQJcW5EEeKtYWeLSRUYTSvOmhUptP3ZGCUchhf6b+SpU0/qyOUGvsipbaVK20qZ91MkBdA7FxacnjlnvyKob" +
        "bFNGC6hc0d4gZt/O3bx0RcvDB41tFxNaTolGTsVQwXliGaIZeFFJlgWYsvRcxvIOq0F1Xrh1KxbrxFEz+KHYKeGNxdlryKUa" +
        "Z6nHfzaapLzVlrlI0K4n1P0IvF7OWK5zIchlxR01yRsGeZVuJQG0GQQFskyW5Mzf1Aw/GivtsDiuXHTAyvgjLT0PmY3F1VRrz" +
        "W/6A3ezqg+lSVD/iH0iETTZ46VDcUp5HJkY/MSK8vFNxKSVtk+eUHKoqjy/7MGkNDQ6Q5gbNGuJ/lt5q3gg+zFrnNq4gOc52R" +
        "IOgaNWtPqR5KDoyx0k4GINJJe9xAJdTPCARTYnyUV5yukkccRNXHMnM56lFKWrBjC3QPbrU55JLia8/avDkq6V9EW5tEPedpZ" +
        "FECGNc9x1dWjQK5YQc61GfJTq5MpdRiQWOIyO/pHePyR08gaKj8sY+aEuIfy8RyxEnyTpJ6nHtowctynpKKE25EMxoMPm48Sh" +
        "05xTUtuw0qOhXV2dIHM7Mg6xmmeoHLiqVcWp0c1ZtDDBM3rGOAA1BNC3wOyoL6+zYd3vyo4UB8HcfFVj5KClTz8eCBlkJNAiU" +
        "mwHFIltkEjWh+HsHQ7ZbHgUMLY6vyWqueAyRENrVup1aRwc3hkqm9LoFchgcdBWrHfldsiStANtMEimDtNeClAVWIH4sIa7EO" +
        "AzCu7rsklQZGjwOdRSmdPVLlSGQbkQgJF2ytIboDqguNaEjSmWymslhjaMx2h5lLc0OUGG9CBEZwJsm0NDwdsXHYLU9O7K0RY" +
        "o3sJjwnsEEOHdNeedfJZ+xTODaRxAA6vc7DXwFMx6JWm0vLTGHDCTnQa+eq7kqo7i+VpgFkteIa/vmEc1w/fyVSYiHNprX2Vy" +
        "YKAc8vA8UvroO77OFlSCDgeNHjU8nD7ysoZS4jHhbKNHtza7k8HbkVVivmMlJG+pocnDun5Jscr6YmeFdxA+kHRUPa6aztwSM" +
        "FZoRpT/Ni5cW7LHmN4XqNjtbgWuGT2Gra+7DxaR9EP0juSLG2WIUbK3rA38Ncy3yzH+k8FStkklxZ5w2N5V7dFm3crNtgaNlM" +
        "IKI1BAub9iqEkurXFoIz7eKUxIa02rI5rPfauaRtfEleconsOSPZOgd7j7MT+EHTkFheklrMks51PXYTzIDh/tCh6FX9hd1JO" +
        "TiKebhX2U1+QYLRO2mfWiTxa6pB/5j6KpP+Dz5RqYdccXatMrQA1sYY3OoDiBiAPkf1KrGa0XR6Nv2Qt/E+TF44sOfkAqy8Lv6" +
        "sVbmPcLprSNg9lfgG6jtVxdaG4g+lC5tN2g5nwzTXu9Fc9Hp+rxPxZUwkGpoNUmCuQ7JqJTfw9gbgFaUprw2yRFmZCGOjkjyNC" +
        "14zcwj5Iu3dXISWHAeGo/sq2Vrm6jz2K2blFnQUZLRFNdLRn906Paas8xq1NZdQJoAXflJKnbJhzaSDuNiu9YK1oWni3Q+LUHK" +
        "+hiVdgpu1ladoKOe6iO68E8Hdko9khGYIf8AH6pxnB1y5H6hByaD4xZnbTd84FercRxFCPZBwQu1LTXwOS2sEwAy9v7JwJIJDS" +
        "QNThJp4kDJGsrXgW8Kb7KK6bVPEatGFp1DhqPBE26Z0tSQADq1unjnvzRlqIeKHUaaj3QAaR91d+Rs38SRFHburo10YczSoyePP" +
        "dWsYbk4PBadDv4EbFAugxCjhlyCnslnYw5NFf6nj5lY2jUmFWuBzbQ3qR1sbg0mhpSoGIGtMJBrSvBWdnuuCNxlmrI+pwRfcbwLy" +
        "c3nyom3djkq0TQxjmS0Zc6URTrts4P8y3B3ERRuf7nJFb8ICl5YHLaC52QAHBDyEDIZ8Vah9jYDhZPLze9sbPRgr8FWSysrllyb" +
        "UgDxJJS2qGpnLDE3FUmnPU+QVrKzrKNY2jG5kn1JcePJDXbE5xqyAvpvhLv7K+bI8ijoCORoB6LYoGUqM+9mZPFROj332VvarE8" +
        "nJlByNU6z3SRm/wBAcz9F3B2bzVAMxzDfxDLk6laeafM4mzxSf5MkjXc2Hq3U/U5/6iobzlOIE0BDm8gKEZBQWi1VYYW6vlJpyq" +
        "0AerR6p2N7Jsq0OnycRwNFEXou3x4XubwKAkaVZZHQwlJcwlJYdRgn1BzSD0RaTXZCdWUlwHrJ7CrHaCx7XjYg+hXpF+M+0RstM" +
        "ebsNHAffZ95viDUrzSGyOK2XRm8TCOrf3DT/Sdj4cVyVdnN8nos+j82FrwDVriHDkaUd8B51R8smJV14WXA7rI9DmQNPzfv+4ZH" +
        "a6/v38ES6ozzY60WJr60FHct1TWppDOyTXdXYmqhrSwO1yPsfokzg+0UQmumUkYDtCWv5a/3CJs85BDH75NOxOwcNiVy02T9/Qq" +
        "CNkmIA0LeNcx9VyyJqpHPG1uIQ6h08Kbj+y62PavrkpY3sLnF+VSSNdCeIXLSGg1aSfL5pLWrQ1S3TIpYDkaEA6GlPQpoLvHx+q" +
        "nMxLQ0k0GYFcgkG5aoBlA7xX7pHuuFtYxGZXBoLnUBc2pdSpdx0CIIKVPBdZ1AYsg2kd+tSCx/1v8A1FTUHAJ/U5aALrOogFlbv" +
        "n4klTRQsB09AFGWU+lSpIms3B9SuMCIonEVDcgaV5q4g6O2ogERhoNKVLQc96E1Qd1OaHV5EUNSMxRej3GIC2heQaAnE4bZZE6h" +
        "Ox41LsTlyuPRkbJ0Pnc7+a+Nja/efWvg1v1W9unoxZo2ikcZP4gw/wC8uKX2uyRmpkaXDepcfKi5J0sgHdxOpwAA9/oqIwhEmlP" +
        "JMlvS6zh7M0rdqNIA9KKss9kijNZbSXU1a5tXHzCht3SqR+TAGD1PqqR5LjUn/wA8VkpR8GxhKtlxbbwjz6toDdi7M+XBUr7eXH" +
        "C0eaooZ3yTdWCS1p7ZGmugPE6eaubdaI7O0n0Fa58EtScv8GuKiVXSAMFBv3jz4D19gVF0XsZfIJT3W93gXDIu8Bn5rt33RJa3d" +
        "ZLVkVa838uQWivSRsUOBgwimEU1zy9KLLo5q9FFapA5zncT7bIYNUzmmijtEoYwuOys6RF2xnVlJUbrfKTWtOSSV+aI/wDXmVkd" +
        "kqimWAcEdJZXN4EcQk0FGqEvRB1eEaKxvSyCNkTqd5tHeOoPufRQFquLcBNBhGuHLxGiXlVobhdSsprNeboThdVzPccx9EaWNf2on" +
        "DPUUq0nmNQeY91nmzVGF22SdHI6M1aT5JUZ1pjpQTdotHWksPbaW89W+TvrRTsmB5pXfeTX5ObXjTP1ao5rM0kuiGAf06ebUxOxb" +
        "0T4gckNLANkO6WRurcQ4tOfoUmXgw5F2E8HDD8UEoWMjOh7YK65IoODGFtExproapryg40HysDxrocuytpmocSU0NTJutPNSCVCY" +
        "wibPhIqddv7+67jZvKiRseI6IuWgaBuoA6hFN0W2yPcC7D2Rqdh48ENG2BgJzAN8udF0tplqntbsVx1j4G5+evzV6LKB95zstm5V" +
        "4ZnTmql1lLaVBHCuVfBXVzWhpoJHYQN02C3sXOWhOYaaFF3Rc8rmkMgc4k989kU4VdqtFdzI4GV6yGri52N1MRblhpuhbf0qiZkL" +
        "TjdoGtoPc7J6xpbZO8rekiNvRKfWrGn8xPwCqZ+j1p6wte5jmAVJ6zqoxx6w97015Kee0WuYYsXVt4vdgb+p+v+lpTobHZ2dq0GS" +
        "Z2tKObFXk6QAnxa1c4x8IxTl5ZFYAGODYW/a5R3RHGYrLGdnVzdKeZJHgi4Oiwjd1tre0urXD9xtTXC1o18PdRR9KH9pkTGQtGob" +
        "/udqSqy13uXbOceLsh7pMsi6Q2MJPbLq33i2lGijdq0xH00HJZi3WsvkA2ALj8APj6KCa2OPeIqdhUnwQDpi1zqihrQg7UyAW4o8" +
        "pWzMrUY0i0a4qov2UijeOZRDLUqi/ZquHgqsj/kmwr+1ZEJwkqgyFdUR6Vovo7bU0JUr5AFRiVHxPxNonQlRHljYU61AJ1jvMteKE" +
        "Ab1VPLUGhUbin3ZOtB18lplJbSjs8tK7oUSkIOaQjnRSiX0OiTOND4SsnxDUGhRENvc3UV5g0d6oGnBcDjol0Nv2X7LwD9HNdyc3C" +
        "8eYyPqnOdG/J7aeVaeuazpcFIy1Pbo404HMe6JSaBcUy0dd7NWPw+BI9lFSVukodydT2qhf4ifvMB8Mv7JG1MdriHkHfRc5I5Romk" +
        "nl3a0+BA+ajbI/8AAT4ZqF0bTpIPMOC4LIdns/WPmh0w1YQ5z943/pKj+0O0zHiF2OxvGj2j/wCxv1U4gl/zG/8A6BZpG7YMy0OxA" +
        "1Ip+6FbHor0iiYHCTGDTsFjQ7XVrgSAWlZssfu5p/1AqOGUCQuO59l1pdHU32GzzNY4941JIoNqnauqfZrwwuDhE51DvlXzoV172" +
        "k1bLhTOup/8x8qpaaCp+y7t1sltUjpXMbGXmoYXZN5KFlhmyxSMYAdgXfIqvhtsTfxOPFT/AMabsw/qPyW83d0dw1VluyxWVxrPP" +
        "apjlWjSB6uJ+CtrJNZIR/w1jJf+KUk08hQe6yQvx+waPU/FDS3jI7V5+A9AteWXhArCvLNnbLylc4l8rGDXC0Bv/SKlBWy9oya5v" +
        "P6W/VZVsu+6cJELlKXbDUIR6Lh95mmFtGN4NFENJPStczwQkbXE8B7o9rGsaJD3RnnqTt5brNIJWxzZuoaHOp1zh2G/5bT988+Hq" +
        "prsis7u+Kk/1En1VA6USEyE1Ls6nNS2aQg6pTzyT0WR+JBx3s2zuilnmbWGV0b+Du0yvx91g+lN2zWZ4ZO2le68ZscOTuPLVb/o3M" +
        "CAM6rUWywRzRGKVgexwoQfiDseaqx5nKOzz8vx1CVxPnrEElt7w/wptYkd1Ekboq9gvJDqcHUGo08kkfAV+UxIeETBaVVY1MyWiNR" +
        "FykGukqanVNxIcSJzXpgk5M2qZAcqHb4KZMczdc1aNi6ZM1d60bqMOyTKqdoqi7Q97QmUXCU1zlyOaHVKVUwPTw4LmcjmJLEkVyi" +
        "46yXEmFy4F0LKN5EjHJwemAldAXUdyJA9OD1GGngpGsKxmpse1ylBUbWjdENDeKF0FsiMikZU7J9W8h4/RIS10z9h9UIVBMUHEqY" +
        "SsbkBiPAfvJRRsO59MvfVPFBk3LisbNSOSvc/I5DSg+ZTLyt47MY7oAHDIDVctEpJDRq40HnxKr7xs5jOfeBoRUarkrCujthecNK" +
        "Vz4o2JxB7oHmqiOTmfRERScylThstxypG+6L2k4hVzRVb6KfIdoLyvo9OGkVBK9Ju2XE0HDTx4JmFaon+T3ZcMmy19klGx+SSp2Q" +
        "aPloFPapBErGx3K94DjRjToXakcQ3cc8gn2kTJNvRXtcio4Xfhd6FaGzxRwD+W3tDMvdTE7lXYeCGt9rc459v1A/ulPL6Hx+O62V" +
        "LSnFKeQ/hGWW+SjimB5EfDijjOxc8biSMNCmPFPD95J5Uhpry9CsmvJ2OXgEJTZMlI55/8ZfBRFyWOsbVdXCFwBadY+qQK5RKiw0" +
        "dVKpXKLoXGDg8roeeaYE4FYzUPxn9lODimhPaEISHgHiiGRDckqJqIiKFsNIkYwJ0baLhdsBUrsTCTxPJAw0TOly5LpJAqRTgFPZ" +
        "3OjNMZbxDaYj47N+PJR9MLYWSOaXueWhrQ52oGEENHqtSsyUqAWPaXFtc9zXfkhb1jI5mvqqhsxBrXNWH2/GM09woljktkTJPFTR" +
        "S04ofGAU/reaW4l0MmuzR3XeIBGQ8yvQrmvZhA7QFdgPmvHopKFXd1212IBoqf3vshimno3Jxkts9pE44j1SXnbbzkG4/TX3KSp4" +
        "S9Hn84ezFXAwG0RggEVORFR3SthezBhOQ14JJLM3Qfxyq38vohpRRpIyzHySSUrLkUVoObvD5qfo9/wC8hGxqCNiC01B4hJJPh0S" +
        "ZTtvaBM8AUAc6gGQGfBNg+94fNJJUPojXZAd0NJqkkkj2Jq6EklhognBdSXGo6U1JJcjWdSC6ksOJGp7UkljCRKzZTxriSBhoJh7" +
        "x8PorMHDCSMjUCoyNPFdSQMNdAcAQPTsf8SRtRv8A0NSSR4fsLz/QzZTWnNdSVpCTHZKqSSSyiI8FbS5GAQAgAE6mmZSSRY+wM3R" +
        "PVJJJOEH/2Q=="

val fakeUsers = listOf(
    User("김미나", 1, "mina123@naver.com", choco1, 50, listOf(1,2,3)),
    User("조자현", 2, "ssibo12@gmail.com", choco2, 40, listOf(1,2,3)),
    User("유비", 3, "poskei@nate.com", "https://miro.medium.com/max/1400/0*EhfyHg8fBGUEyAE-.png", 60, listOf(1,2,3))
)

val fakeLoginResponse = listOf(
    LoginResponse( Member(1, "mina123@naver.com","김미나","img_xxx",) , "yes"),
    LoginResponse( Member(3, "ssibo12@naver.com","조자현","img_xxx",) , "no"),
    LoginResponse( Member(9, "poskei@naver.com","유비","img_xxx",) , "yes"),
    LoginResponse(null, "no")
)