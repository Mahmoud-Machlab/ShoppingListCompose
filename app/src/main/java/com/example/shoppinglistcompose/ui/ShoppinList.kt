package com.example.shoppinglistcompose.ui


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglistcompose.database.ShoppingMemo
import com.example.shoppinglistcompose.shoppingMemoViewModel

@Composable
fun ListView() {

    val memoList by shoppingMemoViewModel.getAllShoppingMemos()!!
        .observeAsState(initial = emptyList())

    LazyColumn(Modifier.padding(top = 4.dp)) {
        items(memoList) { shoppingMemo ->
            ListItem(shoppingMemo)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun ListItem(memo: ShoppingMemo) {
    val context = LocalContext.current
    var isChecked by remember {
        mutableStateOf(memo.isSelected)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(3f)) {
            ClickableText(
                text = buildAnnotatedString { append(memo.toString() )},
                style = TextStyle(
                    color = if(isChecked) Color.LightGray else Color.DarkGray,
                    textDecoration = if(isChecked) TextDecoration.LineThrough else TextDecoration.None,
                    fontSize = 24.sp
                ),
                onClick = {
                    Toast.makeText(context, "$memo", Toast.LENGTH_SHORT).show()
                }
                )
        }
        Column(Modifier.weight(1f)) {
            Checkbox(
                checked = isChecked, onCheckedChange = { isChecked = it },
                modifier = Modifier.align(Alignment.End)
            )
        }
    }

}