package com.n1rocket.harrypotterapp.ui.screens

import androidx.lifecycle.*
import com.n1rocket.harrypotterapp.model.CharacterHP
import com.n1rocket.harrypotterapp.repository.HarryPotterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseDetailViewModel @Inject constructor(
    val handle: SavedStateHandle,
    val repository: HarryPotterRepository
) : ViewModel() {

    lateinit var house : String

    private val charactersLiveData: MutableLiveData<List<CharacterHP>> by lazy {
        MutableLiveData<List<CharacterHP>>().also {
            loadCharacters()
        }
    }

    fun getCharacters(nameHouse: String): LiveData<List<CharacterHP>> {
        house = nameHouse
        return charactersLiveData
    }

    private fun loadCharacters() {
        // Do an asynchronous operation to fetch characters
        CoroutineScope(Dispatchers.IO).launch {
            val characters = repository.getAllCharactersFromHouse(house)
            charactersLiveData.postValue(characters)
        }
    }
}