package fr.isen.bricearmel.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
                NavHost(navController = navController, startDestination = "categories") {
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
                }
            }
        }
    }
}