package com.example.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    var testNum = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val iv_test = findViewById<ImageView>(R.id.iv_test)
        val bt_test = findViewById<Button>(R.id.bt_test)

        val randomGeneratorImage = GenerateAvatarByNumber()
        val generateImage = randomGeneratorImage.generateImage(0, this, null)

        iv_test.setImageBitmap(generateImage)

        bt_test.setOnClickListener(View.OnClickListener {
            testNum += 1
            val generateImage = randomGeneratorImage.generateImage(testNum, this, null)
            iv_test.setImageBitmap(generateImage)
        })
    }
}
