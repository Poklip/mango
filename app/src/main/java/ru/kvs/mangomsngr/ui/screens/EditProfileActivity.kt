package ru.kvs.mangomsngr.ui.screens

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import ru.kvs.mangomsngr.models.user.ProfileData
import ru.kvs.mangomsngr.ui.theme.MangoMsngrTheme
import ru.kvs.mangomsngr.ui.viewmodels.ProfileViewModel

@AndroidEntryPoint
class EditProfileActivity : ComponentActivity() {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MangoMsngrTheme {
                EditContainer(viewModel, this)
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
        var profileData: ProfileData? = null
        viewModel.getUserDataLocal().observe(owner) { response ->
            profileData = response
        }

        Button(
            onClick = {
                //TODO() галерея
            }
        ) {
            Image(
                painter = rememberAsyncImagePainter(profileData?.avatar),
                contentDescription = "avatar",
                modifier = Modifier.size(128.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        val editableFields = listOf("name", "birthday", "city", "vk", "instagram", "status")
        for (field in editableFields) {
            EditableField(
                fieldName = field,
                profileDataFromResponse = profileData,
                viewModel = viewModel
            )
        }

        Button(
            onClick = {
                viewModel.changeUserDataOnService()
                val intent = Intent(owner, ProfileActivity::class.java)
                owner.startActivity(intent)
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

@Composable
fun EditableField(fieldName: String, profileDataFromResponse: ProfileData?, viewModel: ProfileViewModel) {
    var currentField by remember { mutableStateOf(fieldName)}
    var profileData = profileDataFromResponse
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.LightGray)
            .height(50.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp))
    ) {
        TextField(
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            value = currentField,
            placeholder = { Text (text = profileData?.getThisField(fieldName) ?: "") },
            onValueChange = { enteredValue ->
                currentField = enteredValue
                profileData = profileData?.copyThisField(fieldName, enteredValue)
            },
            modifier = Modifier.background(Color.LightGray),
        )
        VerticalDivider()
        IconButton(
            onClick = {
                viewModel.saveUserData(profileData)
            }
        ) {
            Icon(painter = painterResource(R.drawable.baseline_save_24), contentDescription = "save field")
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}