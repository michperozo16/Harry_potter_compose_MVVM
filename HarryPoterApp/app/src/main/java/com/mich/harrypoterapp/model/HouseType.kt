package com.mich.harrypoterapp.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.mich.harrypoterapp.R

enum class HouseType (@DrawableRes val logo: Int, @ColorRes val color: Int){
    gryffindor(R.drawable.logo_gryffindor, R.color.red),
    Slytherin(R.drawable.logo_slytherin, R.color.green),
    Ravenclaw(R.drawable.logo_ravenclaw, android.R.color.holo_blue_dark),
    hufflepuff(R.drawable.logo_hufflepuff, R.color.yellow),


}