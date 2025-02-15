package com.mich.fundamentoskotlin.classes

fun main (){
    val phone: Phone = Phone(688344971)
    phone.call()
    phone.showNumber()
    //println(phone.number)

    println("************* Herencia *************")
    val smartphone = smartphone(541654654, true)
    smartphone.call()
    println("************* Sobre Escritura *************")
    smartphone.showNumber()
    println("Privado? ${smartphone.isPrivate}")

    println("************* Data classes *************")

    val myUser = User(0,"Miguel", "Perozo", Group.FAMILY.ordinal)
    val bro = myUser.copy(1, "Michael")
    val friend = bro.copy(id = 2, group = Group.FRIEND.ordinal)

   println(myUser.component3())
    println(myUser)
    println(bro)
    println(friend)

    println("************* Funciones de Alcance *************")

    with(smartphone){
        println("Privado? $isPrivate")
        call()
    }

//    friend.group = Group.WORK.ordinal
//    friend.name = "Diego"
//    friend.lastName = "Diaz"
    friend.apply {
        group = Group.WORK.ordinal
        name = "Diego"
        lastName = "Diaz"
    }
    println(friend)
}