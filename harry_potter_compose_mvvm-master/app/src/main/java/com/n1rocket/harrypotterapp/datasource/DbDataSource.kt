package com.n1rocket.harrypotterapp.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.n1rocket.harrypotterapp.model.CharacterHP
import com.n1rocket.harrypotterapp.model.CharacterDao


@Database(entities = [CharacterHP::class], version = 1)
abstract class DbDataSource : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}