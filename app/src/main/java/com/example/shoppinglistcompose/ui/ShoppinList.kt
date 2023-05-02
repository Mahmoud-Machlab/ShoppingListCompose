package com.example.shoppinglistcompose.ui


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.lightColors
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItem(memo: ShoppingMemo) {
    val context = LocalContext.current
    var isChecked by remember {
        mutableStateOf(memo.isSelected)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(3f)
            .combinedClickable(
                onLongClick = {
                    Toast.makeText(context, "Long Click", Toast.LENGTH_SHORT).show()},
                onDoubleClick = {
                    Toast.makeText(context, "Double Click", Toast.LENGTH_SHORT).show()},
            ){ Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()}
        ) {
            Text(
                text = buildAnnotatedString { append(memo.toString()) },
                style = TextStyle(
                    color = if (isChecked) Color(0xFFD0BCFF) else Color.DarkGray,
                    textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
                    fontSize = 24.sp
                ),
               modifier = Modifier
                   .combinedClickable(
                   onLongClick = {
                       Toast.makeText(context, "Long Click", Toast.LENGTH_SHORT).show()},
                   onDoubleClick = {
                       Toast.makeText(context, "Double Click", Toast.LENGTH_SHORT).show()},
               ){ Toast.makeText(context, "Click Text", Toast.LENGTH_SHORT).show()}
            )
        }
        Column(Modifier.weight(1f)) {
            Checkbox(
                checked = isChecked, onCheckedChange = {
                    isChecked = it
                    memo.isSelected = it
                    shoppingMemoViewModel.insertOrUpdate(memo)
                },
                colors = CheckboxDefaults.colors(MaterialTheme.colors.primary),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }

}