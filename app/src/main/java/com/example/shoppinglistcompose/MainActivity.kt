package com.example.shoppinglistcompose

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.shoppinglistcompose.ui.InputArea
import com.example.shoppinglistcompose.ui.ListView
import com.example.shoppinglistcompose.ui.theme.ShoppingListComposeTheme
import com.example.shoppinglistcompose.viewmodel.ShoppingMemoViewModel

lateinit var shoppingMemoViewModel: ShoppingMemoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shoppingMemoViewModel = ShoppingMemoViewModel.invoke(application)

        Log.d("TAG", "onCreate: p : ${'p'.isDigit()}")
        Log.d("TAG", "onCreate: leer : ${' '.isDigit()}")
        Log.d("TAG", "onCreate: , : ${','.isDigit()}")
        Log.d("TAG", "onCreate: 9 : ${'9'.isDigit()}")

        if((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        setContent {
            ShoppingListComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FullScreen()
                }
            }
        }
    }
}


@Composable
fun FullScreen(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "ShoppingList",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val memoList = shoppingMemoViewModel.getAllShoppingMemos()
                        memoList?.value?.forEachIndexed() {index,memo ->
                            Log.d("TAG", "FullScreen: ${memo.product} : ${memo.isSelected}")
                            if(memo.isSelected){
                                Log.d("TAG", "FullScreen2: ${memo.product} : ${memo.isSelected}")
                                shoppingMemoViewModel.delete(memo)
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },

    ){
        it.calculateBottomPadding()
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
        ) {
            InputArea()
            ListView()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListComposeTheme {
        FullScreen()
    }
}