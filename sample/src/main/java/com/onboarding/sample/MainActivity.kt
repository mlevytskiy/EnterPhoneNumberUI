package com.onboarding.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.onboarding.enterphonenumberui.LabeledEditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val labeledEditText = findViewById<LabeledEditText>(R.id.phone_number_edit_text)
        labeledEditText.enterPhoneByUser("+3806300000")
        TelegramManager.getInstance().initialize(this)
    }

    public fun onClick(view: View) {
        TelegramManager.getInstance().sendPhoneNumber("+3806300000")
    }

}
