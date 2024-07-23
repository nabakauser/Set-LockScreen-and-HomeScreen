package com.example.wallpaper

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpaper.ui.theme.WallpaperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WallpaperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    WallpaperListViewScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WallpaperListViewPreview() {
    WallpaperListViewScreen()
}

@Composable
fun WallpaperListViewScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Wallpapers",
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
        )
        val images = arrayListOf(
            R.drawable.wp1,
            R.drawable.wp2,
            R.drawable.wp3,
            R.drawable.wp4,
            R.drawable.wp5,
            R.drawable.wp6,
            R.drawable.wp7,
            R.drawable.yoongi,
        )
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalItemSpacing = 4.dp
        ) {
            items(images) { imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clickable {
                            setWallpaper(context, imageRes)
                            Toast.makeText(context,"Wallpaper Applied!", Toast.LENGTH_SHORT).show()
                        }
                )
            }
        }
    }
}

fun setWallpaper(
    context: Context,
    imageId: Int,
) {
    val wallpaperManager = WallpaperManager.getInstance(context)
    val bitmap = BitmapFactory.decodeResource(context.resources, imageId)
    wallpaperManager.setBitmap(bitmap)
}

