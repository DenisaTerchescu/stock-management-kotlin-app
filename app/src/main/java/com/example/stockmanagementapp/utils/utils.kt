package com.example.stockmanagementapp.utils

import android.content.Context
import com.example.stockmanagementapp.data.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

private const val PREFS_NAME = "app_prefs"
private const val PRODUCT_LIST_KEY = "cached_product_list"

fun saveProductListToPrefs(context: Context, productList: List<Product>) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val json = Gson().toJson(productList)
    prefs.edit { putString(PRODUCT_LIST_KEY, json) }
}

fun getCachedProductList(context: Context): List<Product> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val json = prefs.getString(PRODUCT_LIST_KEY, null) ?: return emptyList()
    val type = object : TypeToken<List<Product>>() {}.type
    return Gson().fromJson(json, type) ?: emptyList()
}
