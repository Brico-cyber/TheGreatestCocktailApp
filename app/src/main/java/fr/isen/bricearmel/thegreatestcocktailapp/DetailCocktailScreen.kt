package fr.isen.bricearmel.thegreatestcocktailapp

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCocktailScreen(
    name: String = "Yoghurt Cooler",
    category: String = "Cocktail",
    glass: String = "Highball Glass",
    ingredients: List<Pair<String, String>> = listOf(
        Pair("Yoghurt", "1 cup"),
        Pair("Fruit", "1 cup")
    ),
    instructions: String = "Mix all ingredients together and serve cold."
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(name) },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "$name ajouté aux favoris !", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = name, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Category: $category", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Glass: $glass", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Ingredients", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    ingredients.forEach { (ingredient, measure) ->
                        Text("• $ingredient — $measure")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Recipe", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(instructions)
                }
            }
        }
    }
}