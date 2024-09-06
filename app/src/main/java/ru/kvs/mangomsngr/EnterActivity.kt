package ru.kvs.mangomsngr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.AndroidEntryPoint
import ru.kvs.mangomsngr.ui.theme.MangoMsngrTheme
import ru.kvs.mangomsngr.ui.viewmodels.EnterViewModel

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    private val viewModel by viewModels<EnterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MangoMsngrTheme {
                MainContainer(viewModel, this)
            }
        }
    }
}

@Composable
fun MainContainer(viewModel: EnterViewModel, owner: LifecycleOwner) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        var isWaitingForCode by remember { mutableStateOf(false) }
        var phoneNumber by rememberSaveable { mutableStateOf("") }
        var code by rememberSaveable { mutableStateOf("") }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .height(50.dp)
        ) {
            var expanded by remember { mutableStateOf(false) }
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Show countries")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                repeat(5) {
                    Text("Скопировать", fontSize = 18.sp, modifier = Modifier.padding(10.dp))
                    HorizontalDivider()
                }
            }
            TextField(
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                value = phoneNumber,
                placeholder = { Text (text = "enter phone number")},
                onValueChange = { newNumber ->
                    phoneNumber = newNumber
                },
                modifier = Modifier.background(Color.LightGray),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        if (isWaitingForCode) {
            TextField(
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                value = code,
                onValueChange = { newCode ->
                    code = newCode
                },
                modifier = Modifier
                    .fillMaxWidth(0.19f)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(Color.LightGray)
                    .height(50.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
        Button(
            onClick = {
                //TODO() таймер на кнопку, чтобы не спамили
                if(phoneNumber.isDigitsOnly()) {
                    viewModel.sendAuth(phoneNumber = phoneNumber).observe(owner) { isSuccess ->
                        isWaitingForCode = isSuccess
                    }
                }
            },
            colors = ButtonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            )
        ) {
            Text(text = "Send code")
        }
        Spacer(modifier = Modifier.height(5.dp))
        if (isWaitingForCode) {
            Button(
                onClick = {
                    if(code.isDigitsOnly() && code.length == 6) {
                        viewModel.checkAuth(phoneNumber = phoneNumber, code = code).observe(owner) { answer ->
                            if (answer.isUserExists) {
                                //TODO() к чатам
                            } else {
                                //TODO() к регистрации
                            }
                        }
                    }
                },
                colors = ButtonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                )
            ) {
                Text(text = "Sign in")
            }
        }
    }
}