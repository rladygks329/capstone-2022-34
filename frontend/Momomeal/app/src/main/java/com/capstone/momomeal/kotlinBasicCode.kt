package com.capstone.test01

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.NumberFormatException

/************************************************************
 * kotlin 배웠던 부분 복습용 정리 파일
 *************************************************************/


/************************************************************
 * 1. kt파일은 자바의 텍스트를 붙여넣으면 자동으로 코틀린 파일로 바꿔준다.
 * 2. 세미콜론 아예 안씀
 ************************************************************/

class ExampleActivity : AppCompatActivity() {

    // 3. 전역 변수
    // 변수가 함수 바깥에서 호출될 수도 있다. 그럼 함수 바깥이니까 어느 함수에서든 접근 가능한 전역 변수가 된다.
    // 그러므로 함수 외부 클래스 내부에서는 반드시 초기화를 해줘야 한다.
    var outVar = 15
    val outVal: String = "aaa"
//    var outVal2: float // 이게 안된다는거임.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("안녕하세요") // 4. Log.d("a", 12)같은 로그용 메서드임.
        outVar -= 1 // 3-1. 얘는 바꾸는 게 가능하니까 함수 안에서 바꿀 수도 있음.

        // 1. 변수
        // 변수에는 val, var이 있다.
        val a: Int // 일종의 const 변수임.
        var b: Int // 일반 변수임.

        // 2. 참고로 ": Int"는 초기화가 된 경우에는 안써도 괜찮음. 그럼 자동으로 할당해줌.
        var l = 1

        // 5. replace 관련
        val str: String = "코로나 조심"
        var str2 = str.replace("조심", "이겨내자")
        str2 = "${str.replace("조심", "이겨내자")}!!"
        str2 = str.replace("조심", "이겨내자") + "!!"
        // str2의 새로 할당된 부분들은 다 같은 의미임.

        // 11. List
        val strItems = listOf("apple", "banana", "kiwifruit")
        val strItems2 : List<String> = listOf("apple", "banana", "kiwifruit")
        // strItems = strItems2. strItems에는 List<String>이 숨어있는 것.

        // 12. For문
        for (item in strItems) { // 이렇게 하면 아이템이 반환됨 (ex: apple)
            println(item)
        }
        for (index in strItems.indices) { // 이러면 인덱스 반환 (ex : 0)
            println(index)
            println("인덱스 : $index, 값 :${strItems[index]} ")
            // indices를 사용한 경우, 값을 가져오는 건 알던 대로 ㄱㄱ
            // 근데 텍스트 안에서 리스트 값을 가져오고 싶으면 {} 안에 써야함.
            // 안 그러면 strItems만 가져오고 [index]를 글자로 취급해버림.
        }
        // While문은 늘 쓰던 문장 그대로 사용 가능함.

        // 13-1. When 실사용
        println(describe(1))
        println(describe("Hello"))
        println(describe(5L)) // Long 타입 지정
        println(describe(4f)) // Float 타입 지정정
        println(describe("뚝")) // else로 취급됨

        // 14. 범위 지정
        val x = 10
        val y = 9
        if (x in 1..y+1) {
            println("범위 안이에요")
        }
        if (x >= 1 && x <= y + 1) {
            println("범위 안이에요")
        }
        // 위 둘은 같은 의미를 가진 문장. 편하게 작성 가능!

        // 14-1. 배열에서의 범위 지정
        val list = listOf("a", "b", "c")
        if (-1 !in 0..list.lastIndex) { // 0부터 리스트 범위 안에 없으면
            println("-1이 범위 안에 없음.")
        }
        if (list.size !in list.indices) {
            // 0..size - 1 사이에 사이즈가 존재하면
            println("리스트의 인덱스를 벗어남")
            // 사이즈는 3이니까 0~2 사이에 없겠지.
        }

        // 14-2. 다양한 범위 순환
        for (x in 1..10 step 2) { // 1~10까지 2칸씩 읽는 것
            println(x) // 1 3 5 7 9 나옴
        }
        for (x in 9 downTo 0 step 3) { // 9부터 0까지 내려가면서 3칸씩
            // 내려가는 건 ..이 아니라 무적권 downTo를 사용함.
            println(x)
            // 9 6 3 0 나옴
        }

        // 15. setOf
        val Items = setOf("apple", "banana", "kiwifruit")
        when {
            "orange" in Items   -> println("juicy")
            "apple" in Items    -> println("apple is fine too")
        }

        // 16. List Link
        val fruits = setOf("apple", "banana", "kiwifruit", "avocado")
        fruits
            .filter { it.startsWith("a") } // a로 시작하는 애만 모음
            .sortedBy { it } // 정렬 기능
            .map { it.toUpperCase() } // UPPER CASE로 바꿈
            .forEach { println(it) } // 각 리스트의 항목에 대해 실행함.
                                     // 즉 과일이름이 하나하나 출력됨
        // 이런 식으로 링크 기능을 통해 배열에 이런저런 기능을 추가 가능함.
        // 물론 링크기능은 저거 말고도 엄청 많음.
    }

    // 6. 기본 fun
    //a와 b의 자료형은 Int, 리턴값도 Int라는 뜻.
    fun sum(a: Int, b: Int): Int { // 함수 예약어는 fun!
        return a + b
    }

    fun maxOf(a: Int, b: Int): Int {
        if (a > b) {
            return a
        } else {
            return b
        }
    }

    // 7. 압축된 fun
    fun sum_simple(a: Int, b: Int) = a + b // sum을 이렇게 압축시킬수도 있음.
    // 이러면 Int를 자동으로 반환함. 반환값은 자동으로 지정되고 인자에 따라 달라짐.

    fun maxOf_simple(a: Int, b: Int) = if (a > b) a else b

    // 8. void 함수
    fun printer(a: Int, b: Int): Unit {  // 반환값이 없는 이런 void 함수는 리턴타입 지정을 안해도 됨.
        println("$a 와 $b 의 합은 ${a + b}") //하고싶으면 Unit을 쓰는데, 안 쓰는게 더 깔끔하긴 함
        // $는 변수의 값을 가져온다는 뜻임.
    }

    // 9. nullable
    fun parseInt(str: String): Int? {
        // ?는 nullable을 의미한다. 이러면 null을 리턴해도 상관없음.
        var myNum: Int? = null
        try {
            myNum = str.toInt()
            // 누가봐도 숫자로 변환 불가능한 걸 넣을 수도 있으니까
            // 예외처리를 해 두자.
        } catch (nfe: NumberFormatException) {
            return null
        }

        return myNum
    }

    // 10. Any
    fun getStringLength(obj : Any) : Int? { // null 가능함.
        // Any는 모든 형식의 부모. 어떤 값이든 다 들어갈 수 있음.
        // 정말 뭘 입력하든 다 괜찮은 거니까 함수 내에서는 잘 처리해야 겠지?
        if (obj is String) { // 앞 문장에서 String을 판별했다면 뒤도 String으로 취급
            // 이 안에서는 String으로 유지됨.
            return obj.length
        }
        // 이 함수의 경우 obj가 String이라면 길이를, 아니라면 null을 리턴함
        return null

    }

    // 13. When
    fun describe(obj : Any): String =
        when (obj) {
            1           -> "One" // 하나 걸리면 거기서 끝남. Switch처럼
            "Hello"     -> "Greeting"
            is Long     -> "Long"
            !is String  -> "Not a String"
            else        -> "Unknown"
        }
    // 일종의 Switch문처럼 돌아가는 방식인듯. 사용하기 아주 편해보여요.
    // 실 사용은 위쪽 OnCreate에 있음

    /************************************************************
     * 여기까지는 영상에 나왔던 부분을 정리한 것.
     * 이후로는 내가 개인적으로 찾은 것들을 정리한다.
     *************************************************************/

    // 17. 늦은 초기화 - lazy와 lateinit
    private var mediaPlayer: MediaPlayer? = null // 이 변수는 onCreate 밖에 있음. 전역 변수
    // 전역 변수의 경우 반드시 선 초기화를 해줘야 하지만,
    // 나중에 초기화를 해 주고 싶어서 null을 넣는 경우가 있음. 이건 null 없이 후초기화하는 방법
    private lateinit var mediaPlayerLI: MediaPlayer
    // lateinit은 var을 나중에 초기화할 때 사용함
    // primitive type(bool, int 등)이 아니어야 사용 가능함.
    // 이 변수들은 getter, setter 정의 불가
    fun lateinitTest() {
        if (this::mediaPlayerLI.isInitialized) {
            mediaPlayerLI.start()
        }
    }
//     반드시 이들의 초기화여부를 살펴봐야 하는데, 초기화 체크는 이런 식으로 함.
    /*
    private val mediaPlayerLZ: MediaPlayer by lazy {
        MediaPlayer.create(this, R.raw.anyone)
    }
     이건 Lazy임. val에만 사용 가능.
     by lazy {} 안에 초기화할 때 어떻게 할 건지 작성함.
     미리 코드는 적어두지만 호출 직전에 초기화가 되서 늦은 초기화임.

     늦은 초기화 메모리를 확보해준다는 장점이 있음. 사실 요즘 폰은 워낙 메모리가 커서 애매하지만.
     */

    // 18. nullable 객체 받기
    fun strLenSafe(s: String?): Int =
        if (s != null) s.length else 0 // 함수를 이런 식으로도 만들 수 있음.
    /*
    여기서 data는 nullable이라는 걸 앞에서 미리 설명했다.
    s: String인 경우 인자에 null이 들어가면 NPE(Null Pointer Exception)가 발생한다.

    저 함수는 null이 가능한 건데, 저기서는 s.length을 그냥 반환할 수 없다.
    s가 null인지 아닌지 체크해야 하는데, 그걸 한 방에 정리하는 방법이 바로 ?다.
     */
    fun printAllCaps(s: String?) {
        val allCaps: String? = s?.toUpperCase()
        println(allCaps)// = println(s?.toUpperCase())
    }
    /*
    allCaps는 if (s != null) s.toUpperCase() else null과 같은 의미를 가진다.
    그걸 한 줄로 줄인 거지. 저러면 s가 null 인 경우 .이후의 문장은 실행 없이 null을 반환한다.
    val allCaps: Int? = s?.toUpperCase()?.length처럼 연속적으로도 사용 가능하다.
    물론 저 문장은 UpperCase가 별로 의미는 없겠지만...
     */

    // 함수의 인자가 단 하나인 경우 it이라는 키워드를 통해
}




























