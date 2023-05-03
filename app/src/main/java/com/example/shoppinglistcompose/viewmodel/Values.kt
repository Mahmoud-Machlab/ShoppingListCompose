package com.example.shoppinglistcompose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.shoppinglistcompose.database.ShoppingMemo

object Values : ViewModel() {
    var quantity by mutableStateOf("")
    var product by mutableStateOf("")
    var currentMemo by mutableStateOf(ShoppingMemo(0,""))
    var isDeleted by mutableStateOf(false)
}