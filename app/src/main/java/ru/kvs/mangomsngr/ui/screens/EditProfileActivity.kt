package ru.kvs.mangomsngr.ui.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import ru.kvs.mangomsngr.R
import ru.kvs.mangomsngr.ext.MaskVisualTransformation
import ru.kvs.mangomsngr.models.user.ProfileData
import ru.kvs.mangomsngr.ui.theme.GreyGreenBrighter
import ru.kvs.mangomsngr.ui.theme.LightGrayBrighter
import ru.kvs.mangomsngr.ui.theme.MangoMsngrTheme
import ru.kvs.mangomsngr.ui.theme.RedSoft
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
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        var profileData by remember { mutableStateOf<ProfileData?>(null) }
        viewModel.getUserDataLocal().observe(owner) { response ->
            profileData = response
        }

        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {
                //TODO() галерея
            },
            colors = ButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_placeholder),//rememberAsyncImagePainter(profileData?.avatar),
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
                viewModel = viewModel,
                owner = owner
            )
        }

        Button(
            onClick = {
                viewModel.changeUserDataOnService()
                val intent = Intent(owner, ProfileActivity::class.java)
                owner.startActivity(intent)
            },
            colors = ButtonColors(
                containerColor = GreyGreenBrighter,
                contentColor = Color.Black,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent,
            )
        ) {
            Text(text = "Save changes")
        }
        Button(
            onClick = {
                val intent = Intent(owner, ProfileActivity::class.java)
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
fun EditableField(fieldName: String, profileDataFromResponse: ProfileData?, viewModel: ProfileViewModel, owner: ComponentActivity) {
    var currentField by remember { mutableStateOf("")}
    var profileData = profileDataFromResponse
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(LightGrayBrighter)
            .height(50.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp))
    ) {
        var keyBoardType = KeyboardType.Text
        var visualTransformation = VisualTransformation.None
        var defaultHint = fieldName

        if (fieldName == "birthday") {
            keyBoardType = KeyboardType.Phone
            visualTransformation = MaskVisualTransformation("__.__.____")
            defaultHint = "MM.DD.YYYY"
        }

        IconButton(
            onClick = {
                if (fieldName == "birthday" && currentField.length != 8) {
                    Toast.makeText(owner, "Wrong birthday format.", Toast.LENGTH_LONG).show()
                } else {
                    profileData = profileData?.copyThisField(fieldName, currentField)
                    viewModel.updateUserData(profileData)
                }
            },
            modifier = Modifier.width(24.dp)
        ) {
            Icon(painter = painterResource(R.drawable.baseline_save_24), contentDescription = "save field")
        }
        VerticalDivider(modifier = Modifier
            .width(3.dp)
            .background(Color.Black))
        TextField(
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LightGrayBrighter,
                unfocusedContainerColor = LightGrayBrighter,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            value = currentField,
            placeholder = { Text (text = profileData?.getThisField(fieldName) ?: defaultHint) },
            onValueChange = { enteredValue ->
                currentField = enteredValue
            },
            modifier = Modifier.background(LightGrayBrighter),
            keyboardOptions = KeyboardOptions(keyboardType = keyBoardType),
            visualTransformation = visualTransformation
        )
    }
    Spacer(modifier = Modifier.height(9.dp))
}