package com.zhaldak.myapplication.customviews

import android.content.Context

import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import com.zhaldak.myapplication.R
import kotlinx.android.synthetic.main.contact_group_card.view.*


class ContactGroupCard : CardView {

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }


    private fun init(attrs: AttributeSet?, defStyle: Int) {
        View.inflate(context, R.layout.contact_group_card, this)
        radius = 8.0f
        cardElevation = 8.0f

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                    it,
                    R.styleable.ContactGroupCard, 0, 0
            )

            val titleString = resources.getText(
                    typedArray.getResourceId(
                            R.styleable.ContactGroupCard_title,
                            0
                    )
            )
            titleTxt.text = titleString
            typedArray.recycle()
        }
    }
}