package com.n1rocket.harrypotterapp.repository

import com.n1rocket.harrypotterapp.datasource.RestDataSource
import com.n1rocket.harrypotterapp.model.CharacterDao
import com.n1rocket.harrypotterapp.model.CharacterHP

interface IHarryPotterRepository {
    suspend fun getAllCharactersFromHouse(house: String): List<CharacterHP>
}

class HarryPotterRepository(
    private val dataSource: RestDataSource,
    private val characterDao: CharacterDao
) : IHarryPotterRepository {
    override suspend fun getAllCharactersFromHouse(house: String): List<CharacterHP> {

        try {
            val list = dataSource.getCharactersFromHouse(house)
            list.forEach {
                characterDao.insert(CharacterHP(it.name ?: "", it.image ?: ""))
            }

            return characterDao.getAll()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return listOf()
    }
}