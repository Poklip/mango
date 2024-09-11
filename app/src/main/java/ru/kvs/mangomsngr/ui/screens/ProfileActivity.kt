package ru.kvs.mangomsngr.ui.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import ru.kvs.mangomsngr.R
import ru.kvs.mangomsngr.data.getZodiac
import ru.kvs.mangomsngr.models.user.ProfileData
import ru.kvs.mangomsngr.ui.theme.LightGrayBrighter
import ru.kvs.mangomsngr.ui.theme.MangoMsngrTheme
import ru.kvs.mangomsngr.ui.theme.RedSoft
import ru.kvs.mangomsngr.ui.viewmodels.ProfileViewModel

@AndroidEntryPoint
class ProfileActivity: ComponentActivity()  {

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
        var profileData by remember { mutableStateOf<ProfileData?>(null) }
        viewModel.getUserDataLocal().observe(owner) { response ->
            profileData = response
        }
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.profile_placeholder),
            contentDescription = "avatar",
            modifier = Modifier.size(128.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        val fields = listOf("phoneNumber", "username", "city", "birthday")
        for (field in fields) {
            Field(fieldName = field, profileData = profileData)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .background(LightGrayBrighter)
                .fillMaxWidth(0.5f)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp))
                .padding(10.dp, 2.dp, 10.dp, 2.dp)
                .height(24.dp)
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
                .background(LightGrayBrighter)
                .fillMaxWidth(0.5f)
                .padding(5.dp)
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        IconButton(onClick = {
            val intent = Intent(owner, EditProfileActivity::class.java)
            owner.startActivity(intent)
        }) {
            Icon(painterResource(R.drawable.baseline_edit_24), contentDescription = "edit button")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                val intent = Intent(owner, ChatsActivity::class.java)
                owner.startActivity(intent)
            },
            colors = ButtonColors(
                containerColor = RedSoft,
                contentColor = Color.Black,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            )
        ) {
            Text(text = "Back")
        }
    }
}

@Composable
fun Field(fieldName: String, profileData: ProfileData?) {
    Log.d("KVS_DEBUG", profileData?.getThisField(fieldName).toString())
    Text(
        text = profileData?.getThisField(fieldName) ?: fieldName, modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(LightGrayBrighter)
            .fillMaxWidth(0.5f)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp))
            .padding(10.dp, 2.dp, 10.dp, 2.dp)
    )
    Spacer(modifier = Modifier.height(10.dp))
}