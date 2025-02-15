package com.n1rocket.harrypotterapp.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "character")
data class CharacterHP(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "photo") val photo: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characterHP: CharacterHP)

    @Query("SELECT * FROM character ORDER BY id DESC")
    fun getAll(): List<CharacterHP>


    @Query("SELECT * FROM character WHERE name = :name")
    fun getCharacterById(name: String): CharacterHP

    @Delete
    fun delete(characterHP: CharacterHP)
}