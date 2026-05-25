package fr.isen.bricearmel.thegreatestcocktailapp

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun DrinksScreen(category: String, onDrinkClick: (String) -> Unit = {}) {
    val context = LocalContext.current
    var drinks by remember { mutableStateOf<List<Drink>>(emptyList()) }

    LaunchedEffect(category) {
        NetworkManager.api.getDrinksByCategory(category).enqueue(object : Callback<CocktailResponse> {
            override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>) {
                drinks = response.body()?.drinks ?: emptyList()
            }
            override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
                Toast.makeText(context, "Erreur réseau", Toast.LENGTH_SHORT).show()
            }
        })
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(category) }) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(drinks) { drink ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { onDrinkClick(drink.idDrink) }
                ) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        AsyncImage(
                            model = drink.strDrinkThumb,
                            contentDescription = drink.strDrink,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            drink.strDrink,
                            modifier = Modifier.align(androidx.compose.ui.Alignment.CenterVertically),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}