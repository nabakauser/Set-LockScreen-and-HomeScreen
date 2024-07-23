package com.example.wallpaper.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wallpaper.view.screen.HomeScreen
import com.example.wallpaper.view.screen.SetWallpaperScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home"){
        composable(
            route = "Home"
        ){
            HomeScreen(
                onWallpaperSelected = { imageId ->
                    navController.navigate(
                        "SetWallpaper/resId=$imageId"
                    )
                }
            )
        }
        composable(
            route = "SetWallpaper/resId={imageId}",
            arguments = listOf(
                navArgument(name = "imageId"){
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            SetWallpaperScreen(
                imageId = it.arguments?.getInt("imageId") ?: 0,
                navigateBack = {
                    navController.navigate("Home")
                }
            )
        }
    }
}