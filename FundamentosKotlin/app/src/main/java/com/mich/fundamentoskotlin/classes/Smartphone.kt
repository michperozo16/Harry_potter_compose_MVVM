package com.mich.fundamentoskotlin.classes

open class smartphone (number: Int, val isPrivate: Boolean): Phone(number) {
    override fun showNumber() {
        if (isPrivate) println("Desconocido") else super.showNumber()
    }
}