package com.onboarding.enterphonenumberui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView

class CodeEditText(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private var bindCustomKeyboard: Int? = null
    private var inAppKeyboard: PhoneNumberInAppKeyboard? = null
    private var editText: EditText? = null
    private var label2: TextView? = null

    init {
        val view = View.inflate(getContext(), R.layout.view_enter_code_edit_text, this)
        editText = view.findViewById(R.id.edit_text)
        label2 = view.findViewById(R.id.label3)
    }

    fun setBindCustomKeyboard(value: Int, parentView: View) {
        bindCustomKeyboard = value
        bindKeyboard(parentView)
    }

    fun unbindCustomKeyboard() {
        inAppKeyboard?.setListener(null)
        inAppKeyboard = null
        bindCustomKeyboard = null
    }

    fun setPhoneNumber(value: String?) {
        value?.let {
            label2?.setText(it)
        } ?:run {
            label2?.setText("")
        }
    }

    private fun bindKeyboard(parentView: View) {
        bindCustomKeyboard?.let {
            inAppKeyboard = parentView.findViewById(it)

            inAppKeyboard?.hidePlusButton()
            inAppKeyboard?.setListener(object: OnKeyboardClickListener {
                override fun enterLetter(letter: String) {
                    editText?.text?.append(letter)
                }

                override fun deleteLastLetter() {
                    if (editText!!.text!!.length > 0) {
                        editText?.setText(editText?.text?.substring(0, editText!!.text.length - 1))
                    } else {
                        editText?.setText("")
                    }
                }
            })
        }
    }

}