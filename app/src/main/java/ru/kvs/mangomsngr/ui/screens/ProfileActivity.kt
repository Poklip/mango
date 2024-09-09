package ru.kvs.mangomsngr.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import dagger.hilt.android.AndroidEntryPoint
import ru.kvs.mangomsngr.R
import ru.kvs.mangomsngr.data.getZodiac
import ru.kvs.mangomsngr.models.user.ProfileData
import ru.kvs.mangomsngr.ui.theme.MangoMsngrTheme
import ru.kvs.mangomsngr.ui.viewmodels.ProfileViewModel

@AndroidEntryPoint
class ProfileActivity: ComponentActivity() {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MangoMsngrTheme {
                Container(viewModel, this)
            }
        }
    }

}

@Composable
fun Container(viewModel: ProfileViewModel, owner: ComponentActivity) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        var profileData by rememberSaveable { mutableStateOf<ProfileData?>(null) }
        viewModel.getUserDataLocal().observe(owner) { response ->
            profileData = response
        }
        IconButton(onClick = {
            val intent = Intent(owner, EditProfileActivity::class.java)
            owner.startActivity(intent)
        }) {
            Icon(painterResource(R.drawable.baseline_edit_24), contentDescription = "edit button")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Image(
            painter = rememberAsyncImagePainter("${profileData?.avatar}"),
            contentDescription = "avatar",
            modifier = Modifier.size(128.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "${profileData?.phoneNumber}", modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .fillMaxWidth(0.5f)
                .padding(10.dp, 2.dp, 10.dp, 2.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "${profileData?.username}", modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .fillMaxWidth(0.5f)
                .padding(10.dp, 2.dp, 10.dp, 2.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "${profileData?.city}", modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .fillMaxWidth(0.5f)
                .padding(10.dp, 2.dp, 10.dp, 2.dp)

        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "${profileData?.birthday}", modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .fillMaxWidth(0.5f)
                .padding(10.dp, 2.dp, 10.dp, 2.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .padding(10.dp, 2.dp, 10.dp, 2.dp)
        ) {
            Icon(
                painterResource(getZodiac(profileData?.birthday)?.image ?: R.drawable.ic_launcher_foreground),
                contentDescription = "zodiac icon"
            )
            Text(
                text = getZodiac(profileData?.birthday)?.name ?: "-", modifier = Modifier
                    .padding(10.dp, 0.dp, 10.dp, 0.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = profileData?.status.toString(), modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .background(Color.LightGray)
                .fillMaxWidth(0.5f)
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                val intent = Intent(owner, ChatsActivity::class.java)
                owner.startActivity(intent)
            },
            colors = ButtonColors(
                containerColor = Color.Red,
                contentColor = Color.Red,
                disabledContainerColor = Color.Red,
                disabledContentColor = Color.Red
            )
        ) {
            Text(text = "Back", color = Color.White)
        }
    }
}