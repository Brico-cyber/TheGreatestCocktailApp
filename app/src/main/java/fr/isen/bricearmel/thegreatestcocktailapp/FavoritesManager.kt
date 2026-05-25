package fr.isen.bricearmel.thegreatestcocktailapp

import android.content.Context
import com.google.gson.Gson

object FavoritesManager {
    private const val PREFS_NAME = "favorites"
    private const val KEY = "drinks"

    fun getFavorites(context: Context): MutableList<Drink> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY, null) ?: return mutableListOf()
        return Gson().fromJson(json, Array<Drink>::class.java).toMutableList()
    }

    fun addFavorite(context: Context, drink: Drink) {
        val list = getFavorites(context)
        if (list.none { it.idDrink == drink.idDrink }) {
            list.add(drink)
            save(context, list)
        }
    }

    fun removeFavorite(context: Context, drink: Drink) {
        val list = getFavorites(context)
        list.removeAll { it.idDrink == drink.idDrink }
        save(context, list)
    }

    fun isFavorite(context: Context, drinkId: String): Boolean {
        return getFavorites(context).any { it.idDrink == drinkId }
    }

    private fun save(context: Context, list: List<Drink>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY, Gson().toJson(list)).apply()
    }
}