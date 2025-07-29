package com.example.stockmanagementapp.view.navigator

sealed class Destination(val route: String) {

    object Login : Destination("login")
    object Dashboard : Destination("dashboard")
    object ProductList : Destination("product_list")
    object ProductDetail : Destination("product_detail/{productId}") {
        fun createRoute(productId: Int): String = "product_detail/$productId"
    }

    object EditProduct : Destination("edit_product/{productId}") {
        fun createRoute(productId: Int): String = "edit_product/$productId"
    }

    object SupplierList : Destination("supplier_list")

    data class SupplierDetail(val supplierId: Int) : Destination("supplier_detail/$supplierId")

    object TransactionHistory : Destination("transaction_history")

    object StockManagement : Destination("stock_management")
}

fun Destination.getRoute(): String = route
