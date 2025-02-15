package com.example.torredegracianavarra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.filled.Refresh
import androidx.compose.material3.icons.filled.Send
import androidx.compose.material3.icons.filled.ThumbUp
import androidx.compose.material3.icons.filled.Warning
import androidx.compose.material3.icons.outlined.Add
import androidx.compose.material3.icons.outlined.Search
import androidx.compose.material3.icons.outlined.Send
import androidx.compose.material3.icons.outlined.ThumbUp
import androidx.compose.material3.icons.outlined.Warning
import androidx.compose.material3.surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.featherandroidtasks.ui.theme.FeatherAndroidTasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeatherAndroidTasksTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ImageList()
                }
            }
        }
    }
}

@Composable
fun ImageList() {
    // Mock data for image URLs
    val imageUrls = listOf(
        "https://example.com/image1.jpg",
        "https://example.com/image2.jpg",
        // ... add more URLs
    )

    // Create a vertical scrollable column
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Iterate through the image URLs
        for (imageUrl in imageUrls) {
            // Call the ImageWithButton component for each URL
            ImageWithButton(imageUrl = imageUrl)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ImageWithButton(imageUrl: String) {
    // Placeholder image painter
    val painter = painterResource(id = R.drawable.ic_launcher_foreground)

    // Row containing the image and button
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image component
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(shape = MaterialTheme.shapes.medium)
        )

        // Spacer between image and button
        Spacer(modifier = Modifier.width(16.dp))

        // Button component
        Button(
            onClick = {
                // Handle button click (e.g., open the URL in a browser)
            },
            modifier = Modifier
                .weight(1f)
        ) {
            Text("Abrir en Internet")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ImageListPreview() {
    FeatherAndroidTasksTheme {
        ImageList()
    }
}
