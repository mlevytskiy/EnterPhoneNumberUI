package com.onboarding.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.onboarding.enterphonenumberui.LabeledEditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val labeledEditText = findViewById<LabeledEditText>(R.id.phone_number_edit_text)
        labeledEditText.enterPhoneByUser("+380637674440")
    }

}
