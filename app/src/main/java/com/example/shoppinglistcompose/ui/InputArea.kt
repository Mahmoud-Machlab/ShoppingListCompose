package com.example.shoppinglistcompose.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglistcompose.ui.theme.ShoppingListComposeTheme
import com.example.shoppinglistcompose.viewmodel.Values.product
import com.example.shoppinglistcompose.viewmodel.Values.quantity

@Composable
fun InputArea() {
    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(top = 16.dp, )
                .width(64.dp)
                .weight(1.2f),
            label = { Text(text = "Anz.") }
        )
        OutlinedTextField(
            value = product,
            onValueChange = { product = it },
            modifier = Modifier
                .padding(top = 16.dp, start = 8.dp)
                .weight(3f),
            label = { Text(text = "Artikel") }
        )

        OutlinedButton(
            onClick = {
                quantity = ""
                product = ""
            },
            modifier = Modifier
                .padding(top = 16.dp, start = 8.dp, )
                .height(54.dp)
                .align(Alignment.CenterVertically),

            colors = ButtonDefaults.buttonColors()

        ) {
            Text(
                text = "+",
                style = TextStyle(fontSize = 24.sp)
            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun InputAreaPreview() {
    ShoppingListComposeTheme {
        InputArea()
    }
}