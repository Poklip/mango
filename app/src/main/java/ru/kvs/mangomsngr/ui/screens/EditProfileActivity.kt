package ru.kvs.mangomsngr.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import coil.compose.rememberAsyncImagePainter
import dagger.hilt.android.AndroidEntryPoint
import ru.kvs.mangomsngr.ext.MaskVisualTransformation
import ru.kvs.mangomsngr.models.user.ProfileData
import ru.kvs.mangomsngr.ui.theme.MangoMsngrTheme
import ru.kvs.mangomsngr.ui.viewmodels.ProfileViewModel

@AndroidEntryPoint
class EditProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MangoMsngrTheme {

            }
        }
    }
}

@Composable
fun EditContainer(viewModel: ProfileViewModel, owner: ComponentActivity) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        var profileData by rememberSaveable { mutableStateOf<ProfileData?>(null) }
        viewModel.getUserDataLocal().observe(owner) { response ->
            profileData = response
        }
        var name by remember { mutableStateOf("") }
        var status by remember { mutableStateOf("") }
        Button(
            onClick = {
                //TODO() галерея
            }
        ) {
            Image(
                painter = rememberAsyncImagePainter("${profileData?.avatar}"),
                contentDescription = "avatar",
                modifier = Modifier.size(128.dp)
            )
        }
        TextField(
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            value = name,
            placeholder = { Text (text = profileData?.name ?: "name") },
            onValueChange = { enteredValue ->
                name = enteredValue
            },
            modifier = Modifier.background(Color.LightGray),
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            value = status,
            placeholder = { Text (text = profileData?.status ?: "name") },
            onValueChange = { enteredValue ->
                status = enteredValue
            },
            modifier = Modifier.background(Color.LightGray),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {

            },
            colors = ButtonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            )
        ) {
            Text(text = "Save")
        }
    }
}