package fr.isen.bricearmel.thegreatestcocktailapp

import java.io.Serializable

data class CocktailResponse(
    val drinks: List<Drink>?
)

data class Drink(
    val idDrink: String,
    val strDrink: String,
    val strCategory: String?,
    val strGlass: String?,
    val strInstructions: String?,
    val strDrinkThumb: String?,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?
) : Serializable {
    fun getIngredients(): List<Pair<String, String>> {
        val list = mutableListOf<Pair<String, String>>()
        val ingredients = listOf(strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5)
        val measures = listOf(strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5)
        for (i in ingredients.indices) {
            if (!ingredients[i].isNullOrBlank()) {
                list.add(Pair(ingredients[i]!!, measures[i] ?: ""))
            }
        }
        return list
    }
}

data class CategoryResponse(
    val drinks: List<CategoryItem>?
)

data class CategoryItem(
    val strCategory: String
)