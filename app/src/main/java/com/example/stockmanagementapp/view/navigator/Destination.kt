package com.example.stockmanagementapp.view.navigator

sealed class Destination(val route: String) {

    object Login : Destination("login")
    object Dashboard : Destination("dashboard")
    object ProductList : Destination("product_list")
    data class ProductDetail(val productId: Int) : Destination("product_detail/$productId")
    data class EditProduct(val productId: Int) : Destination("add_product/$productId")

    object SupplierList : Destination("supplier_list")

    data class SupplierDetail(val supplierId: Int) : Destination("supplier_detail/$supplierId")

    object TransactionHistory : Destination("transaction_history")

    object StockManagement : Destination("stock_management")
}

fun Destination.getRoute(): String = route
