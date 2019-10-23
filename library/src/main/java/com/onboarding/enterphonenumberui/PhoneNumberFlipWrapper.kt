package com.onboarding.enterphonenumberui

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class PhoneNumberFlipWrapper(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val rightOut: AnimatorSet
    private val leftIn: AnimatorSet
    private val cardBackLayout: View
    private val cardFrontLayout: View
    private var isBackVisible = false

    init {
        val view = View.inflate(getContext(), R.layout.view_phone_number_flip_wrapper, this)
        rightOut = AnimatorInflater.loadAnimator(context, R.animator.out_animation) as AnimatorSet
        leftIn = AnimatorInflater.loadAnimator(context, R.animator.in_animation) as AnimatorSet
        cardFrontLayout = view.findViewById(R.id.card_front)
        cardBackLayout = view.findViewById(R.id.card_back)
        changeCameraDistance()
    }

    private fun changeCameraDistance() {
        val distance = 3000
        val scale = resources.displayMetrics.density * distance
        cardFrontLayout.setCameraDistance(scale)
        cardBackLayout.setCameraDistance(scale)
    }

    fun flipCard() {
        if (!isBackVisible) {
            rightOut.setTarget(cardFrontLayout)
            leftIn.setTarget(cardBackLayout)
            rightOut.start()
            leftIn.start()
            isBackVisible = true
        } else {
            rightOut.setTarget(cardBackLayout)
            leftIn.setTarget(cardFrontLayout)
            rightOut.start()
            leftIn.start()
            isBackVisible = false
        }
    }

}