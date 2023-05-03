package com.example.shoppinglistcompose.ui


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglistcompose.database.ShoppingMemo
import com.example.shoppinglistcompose.shoppingMemoViewModel
import com.example.shoppinglistcompose.viewmodel.Values
import com.example.shoppinglistcompose.viewmodel.Values.product
import com.example.shoppinglistcompose.viewmodel.Values.quantity

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListView() {

    val memoList by shoppingMemoViewModel.getAllShoppingMemos()!!
        .observeAsState(initial = emptyList())
    val state = rememberLazyListState()
    LazyColumn(
        Modifier.padding(top = 4.dp),
        state = state
    ) {
        // der key ist nötig um auf Änderungen von außen zu reagieren
        items(memoList, key = { memo -> memo.hashCode() }) { memo ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        Log.d("TAG", "ListView: $memo")
                        true
                    } else {
                        false
                    }
                }
            )
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                    if (direction == DismissDirection.EndToStart) {
                        Box(
                            contentAlignment = Alignment.CenterEnd,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.LightGray)
                                .padding(horizontal = 20.dp),

                            ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Löschen",
                                modifier = Modifier.scale(if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f)
                            )
                        }
                    }
                },
                dismissContent = {
                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        Log.d("TAG", "ListView: delete $memo")
                        shoppingMemoViewModel.delete(memo)
                    } else {
                        ListItem(memo)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            )


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
    var showDialog by remember {
        mutableStateOf(false)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            Modifier
                .weight(3f)
                .combinedClickable(
                    onLongClick = {
                        Toast
                            .makeText(context, "Long Click", Toast.LENGTH_SHORT)
                            .show()
                        showDialog = true

                    },
//                onDoubleClick = {
//                    Toast.makeText(context, "Double Click", Toast.LENGTH_SHORT).show()},
                ) {}//{ Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()}
        ) {
            Text(
                text = buildAnnotatedString { append(memo.toString()) },
                style = TextStyle(
                    color = if (isChecked) Color(0xFFD0BCFF) else Color.DarkGray,
                    textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
                    fontSize = 24.sp
                ),
//               modifier = Modifier
//                   .combinedClickable(
//                   onLongClick = {
//                       Toast.makeText(context, "Long Click", Toast.LENGTH_SHORT).show()},
//                   onDoubleClick = {
//                       Toast.makeText(context, "Double Click", Toast.LENGTH_SHORT).show()},
//               ){ Toast.makeText(context, "Click Text", Toast.LENGTH_SHORT).show()}
            )
        }

        // funktionalität für den Dialog
        if (showDialog) {
            quantity = memo.quantity.toString()
            product = memo.product

            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(text = "Artikel ändern")
                },
                text = {
                    Column() {
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = Values.quantity,
                            onValueChange = { Values.quantity = it },
                            shape = RoundedCornerShape(8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .padding(top = 8.dp, start = 4.dp)
                                .width(64.dp),
                            label = { Text(text = "Anz.") }
                        )
                        OutlinedTextField(
                            value = Values.product,
                            onValueChange = { Values.product = it },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(top = 8.dp, start = 4.dp),
                            label = { Text(text = "Artikel") }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        memo.quantity = quantity.toInt()
                        memo.product = product
                        shoppingMemoViewModel.insertOrUpdate(memo)
                        quantity = ""
                        product = ""
                        showDialog = false
                    }) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        quantity = ""
                        product = ""
                        showDialog = false
                    }) {
                        Text(text = "Cancel")
                    }
                }

            )
        }


        Column(Modifier.weight(1f)) {
            Checkbox(
                checked = isChecked, onCheckedChange = {
                    isChecked = it
                    memo.isSelected = isChecked
                    shoppingMemoViewModel.insertOrUpdate(memo)
                },
                colors = CheckboxDefaults.colors(MaterialTheme.colors.primary),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }

}