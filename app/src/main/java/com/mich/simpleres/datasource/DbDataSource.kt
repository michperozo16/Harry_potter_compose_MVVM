package com.mich.simpleres.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mich.simpleres.model.User
import com.mich.simpleres.model.UserDao

@Database(entities = [User::class], version = 1)
abstract class DbDataSource : RoomDatabase() {

    abstract fun userDao(): UserDao
}