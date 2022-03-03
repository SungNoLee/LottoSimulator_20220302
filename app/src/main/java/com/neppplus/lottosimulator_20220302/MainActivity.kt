package com.neppplus.lottosimulator_20220302

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    private fun setupEvents() {
        btnBuyLotto.setOnClickListener {
            buyLotto()
        }
    }

    private fun buyLotto() {
//        6개의 당첨번호 생성
//        코틀린의 for문은 for-each 문법으로 기반.
        for( i in 0 until 6 ) {
//        괜찬은 번호가 나올때까지 무한 반복
            while(true) {
//                1 ~ 45의 랜덤 숫자
//                Math.random()은 0~1 => 1 ~ 45.xxx 로 가공 => Int로 캐스팅
                val randomNum = (Math.random() * 45 + 1).toInt()
                if (true) {
                    break
                }
            }
        }
//        만들어진 당첨번호 6개를 -> 텍스트뷰에 표현
//        보너스번호 생성
//        텍스트뷰에 배치

    }
    private fun setValues() {

    }
}