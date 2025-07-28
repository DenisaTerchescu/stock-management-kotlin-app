package com.example.stockmanagementapp.view.navigator

import androidx.navigation.NavHostController
import javax.inject.Inject

class ComposeNavigator @Inject constructor() : Navigator {

    lateinit var navController: NavHostController

    override fun navigateTo(destination: Destination) {
        navController.navigate(destination.getRoute())
    }


    override fun goBack() {
        navController.popBackStack()
    }
}