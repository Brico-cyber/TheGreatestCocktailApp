package fr.isen.bricearmel.thegreatestcocktailapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random.php")
    fun getRandomCocktail(): Call<CocktailResponse>

    @GET("list.php?c=list")
    fun getCategories(): Call<CategoryResponse>

    @GET("filter.php")
    fun getDrinksByCategory(@Query("c") category: String): Call<CocktailResponse>

    @GET("lookup.php")
    fun getDrinkById(@Query("i") id: String): Call<CocktailResponse>
}

object NetworkManager {
    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    val api: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}