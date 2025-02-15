package com.n1rocket.harrypotterapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.n1rocket.harrypotterapp.model.HouseType
import com.n1rocket.harrypotterapp.ui.screens.HouseDetailScreen
import com.n1rocket.harrypotterapp.ui.screens.HousesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val houses: List<HouseType> = listOf(
        HouseType.Gryffindor,
        HouseType.Slytherin,
        HouseType.Hufflepuff,
        HouseType.Ravenclaw,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    HousesScreen(navController, houses)
                }
                composable(
                    "detail/{house}",
                    arguments = listOf(navArgument("house") { type = NavType.StringType })
                ) { navBack ->
                    HouseDetailScreen(
                        navController,
                        navBack.arguments?.getString("house")!!,
                        hiltViewModel()
                    )
                }
            }


        }
    }
}

//uiMode = UI_MODE_NIGHT_YES
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val houses: List<HouseType> = listOf(
        HouseType.Gryffindor,
        HouseType.Slytherin,
        HouseType.Hufflepuff,
        HouseType.Ravenclaw,
    )

    HousesScreen(rememberNavController(), houses)
}
