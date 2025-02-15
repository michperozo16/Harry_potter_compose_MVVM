package com.example.calendarview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "calendar") {
                composable("calendar") { CalendarScreen(navController = navController) }
                composable("addEvent") { AddEventScreen(navController = navController) }
            }
        }
    }
}

@Composable
fun CalendarScreen(navController: NavHostController) {
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Calendar") }
            )
        },
        content = {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    MonthHeader(
                        month = selectedDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()),
                        year = selectedDate.get(Calendar.YEAR).toString()
                    )
                }
                itemsIndexed(daysInMonth(selectedDate)) { index, day ->
                    if (index % 7 == 0) {
                        WeekHeader()
                    }
                    DayOfMonth(
                        dayOfMonth = day,
                        onClick = {
                            selectedDate.set(Calendar.DAY_OF_MONTH, day)
                            navController.navigate("addEvent")
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun AddEventScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Event") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                }
            )
        },

        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Event name:")
                TextField(value = "", onValueChange = { })
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Date:")
                // TODO: Add date picker
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { /* TODO: Save event */ }) {
                    Text(text =
                    "Save Event")
                }
            }
        }
    )
}

@Composable
fun MonthHeader(month: String, year: String) {
    Text(
        text = "$month $year",
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun WeekHeader() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = "Sun")
        Text(text = "Mon")
        Text(text = "Tue")
        Text(text = "Wed")
        Text(text = "Thu")
        Text(text = "Fri")
        Text(text = "Sat")
    }
}

@Composable
fun DayOfMonth(dayOfMonth: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Text(text = dayOfMonth.toString())
    }
}

fun daysInMonth(calendar: Calendar): List<Int> {
    val days = mutableListOf<Int>()
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    for (day in 1..daysInMonth) {
        days.add(day)
    }
    return days
}
