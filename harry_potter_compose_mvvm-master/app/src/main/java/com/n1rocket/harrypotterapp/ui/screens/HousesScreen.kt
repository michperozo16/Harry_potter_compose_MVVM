package com.n1rocket.harrypotterapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.n1rocket.harrypotterapp.model.HouseType
import com.n1rocket.harrypotterapp.ui.theme.HarryPotterAppTheme


@Composable
fun HousesScreen(navController: NavHostController, houses: List<HouseType>) {
    HarryPotterAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            LazyRow {
                items(items = houses) { item ->
                    House(navController, item)
                }
            }
        }
    }
}

@Composable
fun House(navController: NavHostController, house: HouseType) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(house.logo),
            contentDescription = house.name,
            modifier = Modifier
                .size(280.dp)
                .background(colorResource(house.color))
        )
        Button(
            onClick = {
                print("-----> House clicked")
                navController.navigate("detail/${house.name}")
            }, modifier = Modifier
                .height(100.dp)
                .width(100.dp)
        ) {
            Text("IR")
        }
        Text(text = house.name)
    }
}