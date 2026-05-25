package fr.isen.bricearmel.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.bricearmel.thegreatestcocktailapp.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                var selectedTab by remember { mutableStateOf(0) }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = selectedTab == 0,
                                onClick = { selectedTab = 0; navController.navigate("random") },
                                icon = { Icon(Icons.Filled.Refresh, contentDescription = "Random") },
                                label = { Text("Random") }
                            )
                            NavigationBarItem(
                                selected = selectedTab == 1,
                                onClick = { selectedTab = 1; navController.navigate("categories") },
                                icon = { Icon(Icons.Filled.List, contentDescription = "List") },
                                label = { Text("List") }
                            )
                            NavigationBarItem(
                                selected = selectedTab == 2,
                                onClick = { selectedTab = 2; navController.navigate("favorites") },
                                icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorites") },
                                label = { Text("Favorites") }
                            )
                        }
                    }
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = "categories",
                        modifier = Modifier.padding(padding)
                    ) {
                        composable("random") {
                            DetailCocktailScreen(drinkId = null)
                        }
                        composable("categories") {
                            CategoriesScreen(onCategoryClick = { category ->
                                navController.navigate("drinks/$category")
                            })
                        }
                        composable("drinks/{category}") { backStackEntry ->
                            val category = backStackEntry.arguments?.getString("category") ?: ""
                            DrinksScreen(
                                category = category,
                                onDrinkClick = { drinkId ->
                                    navController.navigate("detail/$drinkId")
                                }
                            )
                        }
                        composable("detail/{drinkId}") { backStackEntry ->
                            val drinkId = backStackEntry.arguments?.getString("drinkId") ?: ""
                            DetailCocktailScreen(drinkId = drinkId)
                        }
                        composable("favorites") {
                            FavoriteScreen(onDrinkClick = { drinkId ->
                                navController.navigate("detail/$drinkId")
                            })
                        }
                    }
                }
            }
        }
    }
}