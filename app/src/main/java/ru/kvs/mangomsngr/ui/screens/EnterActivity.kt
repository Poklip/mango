package ru.kvs.mangomsngr.ui.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import dagger.hilt.android.AndroidEntryPoint
import ru.kvs.mangomsngr.data.Countries
import ru.kvs.mangomsngr.ext.MaskVisualTransformation
import ru.kvs.mangomsngr.ui.theme.MangoMsngrTheme
import ru.kvs.mangomsngr.ui.viewmodels.EnterViewModel

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    private val viewModel by viewModels<EnterViewModel>()
    private val countriesLists = listOf(
        Countries.Russia,
        Countries.Belarus,
        Countries.Georgia,
        Countries.Azerbaijan,
        Countries.Kyrgyzstan,
        Countries.Uzbekistan,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
        //Я бы использовал вариации window.insetsController?.hide(WindowInsets.Type.systemBars()), но у меня 10 андроид
        enableEdgeToEdge()
        setContent {
            MangoMsngrTheme {
                MainContainer(viewModel, this, countriesLists)
            }
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                return
            }
        })
    }
}

@Composable
fun MainContainer(viewModel: EnterViewModel, owner: ComponentActivity, countries: List<Countries>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        var isWaitingForCode by remember { mutableStateOf(false) }
        var phoneNumber by rememberSaveable { mutableStateOf("") }
        var code by remember { mutableStateOf("") }
        var chosenCountry by remember { mutableStateOf(countries.first()) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp))
                .height(50.dp)
        ) {
            var expanded by remember { mutableStateOf(false) }

            IconButton(onClick = { expanded = true }) {
                Image(painterResource(chosenCountry.flag), contentDescription = "countries")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                HorizontalDivider()
                for (country in countries) {
                    IconButton(onClick = {
                        chosenCountry = country
                        phoneNumber = ""
                        expanded = false
                    }) {
                        Image(painterResource(country.flag), "country")
                    }
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
                placeholder = { Text(text = chosenCountry.phoneNumberMask) },
                onValueChange = { enteredValue ->
                    phoneNumber = enteredValue.filter { it.isDigit() || it.code == 43 }
                    chosenCountry =
                        countries.firstOrNull { country -> country.phoneNumberCode == enteredValue }
                            ?: chosenCountry
                },
                modifier = Modifier.background(Color.LightGray),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                visualTransformation = MaskVisualTransformation(mask = chosenCountry.phoneNumberMask),
                readOnly = isWaitingForCode
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
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp))
                    .height(50.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
            Spacer(modifier = Modifier.height(5.dp))
        }

        Button(
            onClick = {
                if (phoneNumber.isDigitsOnly() && phoneNumber.length >= 11) {
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
                    if (code.isDigitsOnly() && code.length == 6) {
                        viewModel.checkAuth(phoneNumber = phoneNumber, code = code)
                            .observe(owner) { response ->
                                if (response.isUserExists) {
                                    viewModel.saveTokens(
                                        accessToken = response.accessToken ?: "",
                                        refreshToken = response.refreshToken ?: ""
                                    )
                                    val chatsIntent = Intent(owner, ChatsActivity::class.java)
                                    owner.startActivity(chatsIntent)
                                } else {
                                    val regIntent = Intent(owner, RegistrationActivity::class.java)
                                    regIntent.putExtra("phoneNumber", phoneNumber)
                                    owner.startActivity(regIntent)
                                }
                            }
                    }
                },
                colors = ButtonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                )
            ) {
                Text(text = "Sign in")
            }
        }
    }
}