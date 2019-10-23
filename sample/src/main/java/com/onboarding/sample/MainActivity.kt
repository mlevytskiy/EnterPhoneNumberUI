package com.onboarding.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.onboarding.enterphonenumberui.LabeledEditText
import com.onboarding.enterphonenumberui.PhoneNumberFlipWrapper

class MainActivity : AppCompatActivity() {

    private var flipWrapper: PhoneNumberFlipWrapper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val labeledEditText = findViewById<LabeledEditText>(R.id.phone_number_edit_text)
        labeledEditText.enterPhoneByUser("+3806300000")
        TelegramManager.getInstance().initialize(this)
        flipWrapper = findViewById(R.id.phone_number_flip_wrapper)
    }

    public fun onClick(view: View) {
        flipWrapper?.flipCard()
//        TelegramManager.getInstance().sendPhoneNumber("+3806300000")
    }

}
