package com.example.stockmanagementapp.view.navigator

sealed class Destination(val route: String) {

    object Login : Destination("login")
    object Dashboard : Destination("dashboard")
    object ProductList : Destination("product_list")
    object ProductDetail : Destination("product_detail/{productId}") {
        fun createRoute(productId: Int): String = "product_detail/$productId"
    }

    object AddNewProduct : Destination("add_product")

    object SupplierList : Destination("supplier_list")

    object SupplierDetail : Destination("supplier_detail/{supplierId}") {
        fun createRoute(supplierId: Int): String = "supplier_detail/$supplierId"
    }
    object TransactionHistory : Destination("transaction_history")

    object StockManagement : Destination("stock_management")
}

fun Destination.getRoute(): String = route
