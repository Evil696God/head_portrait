package com.example.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

  private var testNum = 0;

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    this.setContentView(R.layout.activity_main)

    val iv_test = this.findViewById<ImageView>(R.id.iv_test)
    val bt_test = this.findViewById<Button>(R.id.bt_test)

    val randomGeneratorImage = GenerateAvatarByNumber(0, this, null)
    iv_test.setImageBitmap(randomGeneratorImage.bitmap)

    bt_test.setOnClickListener(View.OnClickListener {
      this.testNum += 1
      val generateImage = GenerateAvatarByNumber(testNum, this, null)
      iv_test.setImageBitmap(generateImage.bitmap)
    })
  }
}
