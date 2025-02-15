package com.n1rocket.harrypotterapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.n1rocket.harrypotterapp.ui.theme.HarryPotterAppTheme


@Composable
fun HouseDetailScreen(
    navHostController: NavHostController,
    nameHouse: String,
    viewModel: HouseDetailViewModel
) {
    val characters by viewModel.getCharacters(nameHouse).observeAsState()

    HarryPotterAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column {
                Text(text = nameHouse)
                characters?.map {
                    Text(it.name)
                }
            }
        }
    }
}