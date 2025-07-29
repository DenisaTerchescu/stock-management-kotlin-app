package com.example.stockmanagementapp.view.navigator

import androidx.navigation.NavHostController
import javax.inject.Inject

class ComposeNavigator @Inject constructor() : Navigator {

    lateinit var navController: NavHostController

    override fun navigateTo(route: String) {
        navController.navigate(route)
    }


    override fun goBack() {
        navController.popBackStack()
    }
}