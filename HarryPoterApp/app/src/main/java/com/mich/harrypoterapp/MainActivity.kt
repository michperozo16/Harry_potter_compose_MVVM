package com.mich.harrypoterapp

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mich.harrypoterapp.model.HouseType
import com.mich.harrypoterapp.ui.theme.HarryPoterAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


        }
    }
}

@Composable
fun App(houses: List<HouseType>) {
    HarryPoterAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background

        ) {

            LazyRow {
                items(items = houses) { item ->
                    House(item)
                }
            }

        }
    }
}

@Composable
fun House(house: HouseType) {

    Column (horizontalAlignment = Alignment.CenterHorizontally){


            Image(
                painter = painterResource(house.logo),
                contentDescription = house.name,
                modifier = Modifier
                    .size(250.dp)
                    .background(colorResource(house.color))
                    .clickable { }
            )

        Text(text = house.name)
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HarryPoterAppTheme {
        val houses: List<HouseType> = listOf(
            HouseType.gryffindor,
            HouseType.hufflepuff,
            HouseType.Slytherin,
            HouseType.Ravenclaw,
        )
        App(houses)
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DerkPreview() {
    HarryPoterAppTheme {
        val houses: List<HouseType> = listOf(
            HouseType.gryffindor,
            HouseType.hufflepuff,
            HouseType.Slytherin,
            HouseType.Ravenclaw,
        )
        App(houses)
    }
}