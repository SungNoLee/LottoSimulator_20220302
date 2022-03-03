package com.neppplus.lottosimulator_20220302

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

//    내 번호 6개
//    코틀린은 단순 배열 초기화 int[] arr={}; 문번 x
    val mMyNumbers = arrayOf(13, 17, 23, 27, 36, 41)
    lateinit var mHandler : Handler
//    핸들러가 반복 실행할 코드()를 인터페이스를 이용해 변수로 저장.
    val buyLottoRunnable = object : Runnable {
    override fun run() {
//    물려받은 추상메쏘드 구현
        if (mUsedMoney < 10000000) {
            buyLotto()

//            핸들러에게 다음 할일로 이코드를 다시 등록
            mHandler.post(this)
        }
        else {
            Toast.makeText(this@MainActivity, "자동 구래가 완료되었습니다.", Toast.LENGTH_SHORT).show()
        }
//        그렇지 않으면
    }

}

//    컴퓨터다 뽑은 당첨번호 6개를 저장할 ArrayList
    val mWinNumberList = ArrayList<Int>()
    var mBonusNum = 0   // 보너스번호는 매 판마다 새로 뽑아야함. 변경소지 0, 화면이 어딘지는 줄 필요X 바로 대입 var

//    당첨번호를 보여줄 6개의 덱스트뷰를 담아둘 Arraylist
    val mWinNumTextViewList = ArrayList<TextView>()
    val winNumCnt = ArrayList<TextView>()

//    사용한 금액, 당첨된 금액 합산 변수
    var mUsedMoney = 0
    var mEarnMoney = 0L     // 30억 이상의 당첨 대비. Long 타입으로 설정

    var rankCount1 = 0
    var rankCount2 = 0
    var rankCount3 = 0
    var rankCount4 = 0
    var rankCount5 = 0
    var rankCountNot = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    private fun setupEvents() {

        btnAutoBuy.setOnClickListener {
            mHandler.post(buyLottoRunnable)
        }

        btnBuyLotto.setOnClickListener {
            buyLotto()
            checkLottoRank()
        }
    }

    private fun buyLotto() {

        mUsedMoney += 1000;
//        6개의 당첨번호 생성
//        코틀린의 for문은 for-each 문법으로 기반.
//        ArrayList는 목록을 계속 누적 가능.
//        당첨번호 뽑기 전에 기존의 당첨번호는 전부 삭제하고 다시 뽑자.
        mWinNumberList.clear()

        for( i in 0 until 6 ) {
//        괜찬은 번호가 나올때까지 무한 반복
            while(true) {
//                1 ~ 45의 랜덤 숫자
//                Math.random()은 0~1 => 1 ~ 45.xxx 로 가공 => Int로 캐스팅
                val randomNum = (Math.random() * 45 + 1).toInt()
//              중복
                if ( !mWinNumberList.contains(randomNum)) {
//                    당첨번호로 뽑은 랜덤 숫자 등록
                    mWinNumberList.add(randomNum)
                    break
                }
            }
        }

//        만들어진 당첨번호 6개를 -> 작은 수
        mWinNumberList.sort()   // 자바로 직접 짜던 로직을 > 객체지향의 특성, 만들어져있는 기능 활용으로 대체.

//        만들어진 당첨번호 6개를 -> 텍스트뷰에 표현
//        Log.d("당첨번호", mWinNumberList.toString())

        mWinNumberList.forEachIndexed { index, winNum ->
            mWinNumTextViewList[index].text = winNum.toString()
        }
//        보너스번호 생성
        while (true) {
            val randomNum = (Math.random() * 45 + 1).toInt()
            if (!mWinNumberList.contains(randomNum)) {
                mBonusNum = randomNum
                break
            }
        }

    //        텍스트뷰에 배치
        txtBonusNum.text = mBonusNum.toString()
    }

    private fun checkLottoRank() {
        var correctCount = 0
//        내 번호 하나씩 조회

        for(myNum in mMyNumbers) {
//            당첨번호를 맟춰는가? => 당첨번호 목록에 내번호가 들어있나?
            if(mWinNumberList.contains(myNum)) {
                correctCount++
            }
        }

        when(correctCount) {
            6 -> {
                mEarnMoney += 3000000000
                rankCount1++
                txtWinNum01.text = rankCount1.toString()
//                Toast.makeText(this, "1등입니다.", Toast.LENGTH_SHORT).show()
            }
            5->{
//                보너스 번호를 맟췄는지? => 보너스번호가 내 번호 목록에 들어있나?
                if(mMyNumbers.contains(mBonusNum)) {
//                    Toast.makeText(this, "2등입니다.", Toast.LENGTH_SHORT).show()
                    mEarnMoney += 50000000
                    rankCount2++
                } else {
//                    Toast.makeText(this, "3등입니다.", Toast.LENGTH_SHORT).show()
                    mEarnMoney += 2000000
                    rankCount3++
                }

            }
            4->{
                mEarnMoney += 50000
//                Toast.makeText(this, "4등입니다.", Toast.LENGTH_SHORT).show()
                rankCount4++
            }
            3->{
                mUsedMoney += 5000
//                Toast.makeText(this, "5등입니다.", Toast.LENGTH_SHORT).show()
                rankCount5++
            }
            else ->{
//                Toast.makeText(this, "낙첨입니다.", Toast.LENGTH_SHORT).show()
                rankCountNot++
            }
        }
        txtUsedMoney.text = "${NumberFormat.getInstance().format(mUsedMoney)} 원"
        txtWinMoney.text = "${NumberFormat.getInstance().format(mEarnMoney)} 원"

    }
    private fun setValues() {
        mWinNumTextViewList.add(txtWinNum01)
        mWinNumTextViewList.add(txtWinNum02)
        mWinNumTextViewList.add(txtWinNum03)
        mWinNumTextViewList.add(txtWinNum04)
        mWinNumTextViewList.add(txtWinNum05)
        mWinNumTextViewList.add(txtWinNum06)

    }
}
