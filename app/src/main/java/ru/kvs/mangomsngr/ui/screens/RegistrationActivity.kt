package ru.kvs.mangomsngr.ui.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import ru.kvs.mangomsngr.ui.theme.MangoMsngrTheme
import ru.kvs.mangomsngr.ui.viewmodels.EnterViewModel

@AndroidEntryPoint
class RegistrationActivity : ComponentActivity() {

    private val viewModel by viewModels<EnterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        enableEdgeToEdge()
        setContent {
            MangoMsngrTheme {
                Container(extras, this, viewModel)
            }
        }
    }
}

@Composable
fun Container(data: Bundle?, owner: ComponentActivity, viewModel: EnterViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        var username by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        var isUsernameLegit by remember { mutableStateOf(true) }
        val phoneNumber by remember { mutableStateOf(data?.getString("phoneNumber") ?: "no data") }

        PhoneNumber(phoneNumber)
        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            value = name,
            onValueChange = { newName ->
                if (!name.contains(Regex("fuck|Fuck|cunt|Cunt|nigger|Nigger|bitch|Bitch|Whore|whore"))) {
                    name = newName
                }
            },
            placeholder = {Text(text = "enter name")},
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .height(50.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(5.dp))

        TextField(
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            value = username,
            onValueChange = { newUsername ->
                isUsernameLegit = newUsername.contains(Regex("[a-zA-Z0-9-_]"))
                username = newUsername
            },
            placeholder = {Text(text = "enter username")},
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .height(50.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = {
                if(isUsernameLegit && username.isNotEmpty() && name.isNotEmpty()) {
                    viewModel.registerNewUser(phoneNumber = phoneNumber, name = name, username = username).observe(owner) { response ->
                        if (response == null) {
                            Toast.makeText(
                                owner,
                                "Error trying register new user.",
                                Toast.LENGTH_LONG)
                                .show()
                        } else {
                            viewModel.saveTokens(
                                accessToken = response.accessToken ?: "",
                                refreshToken = response.refreshToken ?: ""
                            )
                            val chatIntent = Intent(owner, ChatsActivity::class.java)
                            owner.startActivity(chatIntent)
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
            Text(text = "Sign up")
        }
    }
}

@Composable
fun PhoneNumber(phoneNumber: String) {
    Text(
        text = "+$phoneNumber"
    )
}