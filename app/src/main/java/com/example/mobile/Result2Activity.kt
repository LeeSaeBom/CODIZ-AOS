package com.example.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.activity_result2.*

class Result2Activity : AppCompatActivity() {

    var b1Array = arrayOf("num == 1","1","2","3")
    var b2Array = arrayOf("else if","1","2","3")
    var score = 0
    var all = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result2)

        if (intent.hasExtra("choose")) {
            textview1.text = intent.getStringExtra("choose")
            /* "nameKey"라는 이름의 key에 저장된 값이 있다면s
               textView의 내용을 "nameKey" key에서 꺼내온 값으로 바꾼다 */

        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }

        if (btextview1.text == b1Array[0]) {
            score += 1
        }else if(btextview2.text == b2Array[0]){
            score += 1
        }else{
            all -= 1
        }
        bscoretext.setText("${score}개 맞추고, ${all}점 입니다.")
    }
}
