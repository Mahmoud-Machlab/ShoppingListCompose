package com.example.shoppinglistcompose.ui



import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListView(){
    LazyColumn(Modifier.padding(top = 4.dp)){
        items(emptyList<String>()){
           ListItem()
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun ListItem(){

}