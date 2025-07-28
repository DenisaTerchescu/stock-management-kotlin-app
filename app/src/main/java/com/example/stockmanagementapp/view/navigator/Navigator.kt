package com.example.stockmanagementapp.view.navigator

sealed class Destination(val route: String) {

    object Login : Destination("login")
    object Dashboard : Destination("dashboard")
    object ProductList : Destination("product_list")
    data class ProductDetail(val productId: Int) : Destination("product_detail/$productId")
}

fun Destination.getRoute(): String = when (this) {
    is Destination.ProductList -> route
    is Destination.ProductDetail -> route
    Destination.Login -> route
    Destination.Dashboard -> route
}

interface Navigator {
    fun navigateTo(destination: Destination)
    fun goBack()

}

