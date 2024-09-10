package ru.kvs.mangomsngr.ui.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import ru.kvs.mangomsngr.R
import ru.kvs.mangomsngr.ui.theme.GreyGreen
import ru.kvs.mangomsngr.ui.theme.MangoMsngrTheme
import ru.kvs.mangomsngr.ui.viewmodels.ProfileViewModel

@AndroidEntryPoint
class ChatsActivity : ComponentActivity() {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideUI(window)
        enableEdgeToEdge()
        setContent {
            MangoMsngrTheme {
                ChatsContainer(this, viewModel)
            }
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                hideUI(window)
                return
            }
        })
    }
}

private fun hideUI(window: Window) {
    window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            )
}

@Composable
fun ChatsContainer(owner: ComponentActivity, viewModel: ProfileViewModel) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .fillMaxHeight()
                .background(Color.White)
                .padding(0.dp, 5.dp)
        ) {
            var avatar by remember { mutableStateOf("") }
            var name by remember { mutableStateOf("")}
            viewModel.getUserDataLocal().observe(owner) { profileData ->
                avatar = profileData?.avatar ?: ""
                name = profileData?.name ?: "profile name"
            }
            Image(
                painter = painterResource(id = R.drawable.kyrgyzstan),//rememberAsyncImagePainter(profileData?.avatar),
                contentDescription = "avatar",
                modifier = Modifier
                    .size(96.dp)
                    .clip(shape = RoundedCornerShape(64.dp))
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(64.dp))
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
            )
            Button(
                onClick = {
                    val intent = Intent(owner, ProfileActivity::class.java)
                    owner.startActivity(intent)
                },
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                )
            ) {
                Text(text = name, color = Color.Black)
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Black)
            )
            var chats = emptyList<Any>()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .background(GreyGreen)
            ) {
                /*for (chat in chats) {

                }*/
            }
        }

        Spacer(modifier = Modifier
            .fillMaxHeight()
            .background(Color.Black)
            .width(1.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.LightGray)
                .padding(4.dp),
        ) {
            var messageToSend by rememberSaveable { mutableStateOf("") }
            var messages = emptyList<String>()
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .background(GreyGreen)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp))
            ) {
                /*for(message in messages) {

                }*/
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp)),
            ) {
                TextField(
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    value = messageToSend,
                    placeholder = { Text(text = "enter message") },
                    onValueChange = { enteredValue ->
                        messageToSend = enteredValue
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight()
                        .background(Color.White)
                )
                VerticalDivider(
                    modifier = Modifier
                        .background(Color.Black)
                        .width(3.dp)
                )
                IconButton(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = {
                        //TODO()
                    }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_send_24),
                        contentDescription = "send message"
                    )
                }
            }
        }
    }

}