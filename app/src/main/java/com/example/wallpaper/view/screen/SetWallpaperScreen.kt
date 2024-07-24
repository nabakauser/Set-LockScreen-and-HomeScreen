package com.example.wallpaper.view.screen

import android.app.WallpaperManager
import android.app.WallpaperManager.FLAG_LOCK
import android.app.WallpaperManager.FLAG_SYSTEM
import android.content.Context
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Preview
@Composable
fun SetWallpaperPreview() {
    SetWallpaperScreen(
        imageId = com.example.wallpaper.R.drawable.wp1,
        navigateBack = {},
    )
}

@Composable
fun SetWallpaperScreen(
    imageId: Int,
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = imageId),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
        )
        Row(
            modifier = Modifier
                .padding(bottom = 56.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    isLoading.value = true
                    coroutineScope.launch {
                        setWallpaper(
                            context,
                            imageId,
                            true
                        )
                        Toast.makeText(context, "Lockscreen Applied Successfully!", Toast.LENGTH_SHORT).show()
                        isLoading.value = false
                        navigateBack()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray.copy(alpha = .4f),
                    contentColor = Color.DarkGray
                )
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "Set Lockscreen",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    isLoading.value = true
                    coroutineScope.launch {
                        setWallpaper(
                            context,
                            imageId,
                            false
                        )
                        Toast.makeText(context, "Homescreen Applied Successfully!", Toast.LENGTH_SHORT).show()
                        isLoading.value = false
                        navigateBack()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray.copy(alpha = .4f),
                    contentColor = Color.DarkGray
                )
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "Set Homescreen",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        if (isLoading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        }
    }
}

suspend fun setWallpaper(
    context: Context,
    imageId: Int,
    isLockScreen: Boolean,
) {
    withContext(Dispatchers.IO) {
        val bitmap = BitmapFactory.decodeResource(context.resources, imageId)
        val wallpaperManager = WallpaperManager.getInstance(context)
        wallpaperManager.setBitmap(
             bitmap,
            null,
            false,
            if(isLockScreen) FLAG_LOCK else FLAG_SYSTEM
        )
    }
}