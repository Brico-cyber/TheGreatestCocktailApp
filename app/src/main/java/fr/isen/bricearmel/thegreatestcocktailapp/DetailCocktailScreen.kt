package fr.isen.bricearmel.thegreatestcocktailapp

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCocktailScreen(drinkId: String? = null) {
    val context = LocalContext.current
    var drink by remember { mutableStateOf<Drink?>(null) }

    LaunchedEffect(drinkId) {
        val call = if (drinkId != null) {
            NetworkManager.api.getDrinkById(drinkId)
        } else {
            NetworkManager.api.getRandomCocktail()
        }
        call.enqueue(object : Callback<CocktailResponse> {
            override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>) {
                drink = response.body()?.drinks?.firstOrNull()
            }
            override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
                Toast.makeText(context, "Erreur réseau", Toast.LENGTH_SHORT).show()
            }
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(drink?.strDrink ?: "Chargement...") },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "${drink?.strDrink} ajouté aux favoris !", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
                    }
                }
            )
        }
    ) { padding ->
        if (drink == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                CircularProgressIndicator(modifier = Modifier.align(androidx.compose.ui.Alignment.Center))
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = drink!!.strDrinkThumb,
                    contentDescription = drink!!.strDrink,
                    modifier = Modifier.fillMaxWidth().height(250.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(drink!!.strDrink, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Category: ${drink!!.strCategory ?: "-"}")
                Text("Glass: ${drink!!.strGlass ?: "-"}")
                Spacer(modifier = Modifier.height(16.dp))

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Ingredients", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        drink!!.getIngredients().forEach { (ingredient, measure) ->
                            Text("• $ingredient — $measure")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Recipe", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(drink!!.strInstructions ?: "-")
                    }
                }
            }
        }
    }
}