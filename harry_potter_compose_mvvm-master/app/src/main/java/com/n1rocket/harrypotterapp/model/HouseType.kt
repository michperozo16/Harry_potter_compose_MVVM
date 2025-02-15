package com.n1rocket.harrypotterapp.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.n1rocket.harrypotterapp.R

enum class HouseType(@DrawableRes val logo: Int, @ColorRes val color: Int) {
    Gryffindor(R.drawable.logo_gryffindor, R.color.red),
    Slytherin(R.drawable.logo_slytherin, R.color.green),
    Ravenclaw(R.drawable.logo_ravenclaw, R.color.blue),
    Hufflepuff(R.drawable.logo_hufflepuff, R.color.yellow),

}