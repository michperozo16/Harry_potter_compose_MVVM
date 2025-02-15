package com.mich.myplayer

abstract class Person (name:String, val age:Int) {
    val name = name
    get() = "Name: $field"
}

class Debeloper(name: String,):Person(name,20)
{

    fun test(){

        val d = Debeloper("tom")
        val name = d.name
    }
}