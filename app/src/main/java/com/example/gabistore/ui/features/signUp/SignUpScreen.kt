package com.example.gabistore.ui.features.signUp

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gabistore.R
import com.example.gabistore.ui.theme.BackgroundMain
import com.example.gabistore.ui.theme.Blue
import com.example.gabistore.ui.theme.MainAppTheme
import com.example.gabistore.util.MyScreens
import com.example.gabistore.util.NetworkChecker
import com.example.gabistore.util.VALUE_SUCCESS
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {

    MainAppTheme {
        Surface(
            color = BackgroundMain,
            modifier = Modifier.fillMaxSize()
        ) {
            SignUpScreen()
        }
    }

}

@Composable
fun SignUpScreen() {
    val uiController = rememberSystemUiController()
    SideEffect { uiController.setStatusBarColor(Blue) }

    val context = LocalContext.current
    val navigation = getNavController()
    val viewModel = getNavViewModel<SignUpViewModel>()

    clearInputs(viewModel)

    Box {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(Blue)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            IconApp()

            MainCardView(navigation, viewModel) {

                viewModel.signUpUser {

                    if (it == VALUE_SUCCESS) {



                        navigation.navigate(MyScreens.MainScreen.route) {
                            popUpTo(MyScreens.MainScreen.route) {
                                inclusive = true
                            }
                        }

                    } else {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }

                }

            }

        }
    }
}

@Composable
fun IconApp() {

    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .size(64.dp),
        color = Color.White
    ) {

        Image(
            modifier = Modifier.padding(14.dp),
            painter = painterResource(id = R.drawable.ic_icon_app),
            contentDescription = null
        )

    }

}

@Composable
fun MainCardView(navigation: NavController, viewModel: SignUpViewModel, SignUpEvent: () -> Unit) {
    val name = viewModel.name.observeAsState("")
    val email = viewModel.email.observeAsState("")
    val password = viewModel.password.observeAsState("")
    val confirmPassword = viewModel.confirmPassword.observeAsState("")
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = ShapeDefaults.Medium,
        colors = CardDefaults.cardColors(Color.White)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.padding(top = 18.dp, bottom = 18.dp),
                text = "Sign Up",
                style = TextStyle(color = Blue, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            )

            MainTextField(
                name.value,
                R.drawable.ic_person,
                "Your Full Name"
            ) { viewModel.name.value = it }
            MainTextField(email.value, R.drawable.ic_email, "Email") { viewModel.email.value = it }
            PasswordTextField(
                password.value,
                R.drawable.ic_password,
                "Password"
            ) { viewModel.password.value = it }
            PasswordTextField(
                confirmPassword.value,
                R.drawable.ic_password,
                "Confirm Password"
            ) { viewModel.confirmPassword.value = it }
            Button(
                onClick = {
                    if (name.value.isNotEmpty() && email.value.isNotEmpty() && password.value.isNotEmpty() && confirmPassword.value.isNotEmpty()) {
                        if (password.value == confirmPassword.value) {
                            if (password.value.length >= 8) {
                                if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                                    if (NetworkChecker(context).isInternetConnected) {
                                        SignUpEvent.invoke()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "please connect to internet",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "email format is not true",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "password characters should be more than 8!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "passwords are not the same!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    } else {
                        Toast.makeText(context, "please write data first...", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(Blue)
            )
            {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Register Account"
                )
            }

            Row(
                modifier = Modifier.padding(bottom = 18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text("Already have  an account?")
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = {

                    navigation.navigate(MyScreens.SignInScreen.route) {
                        popUpTo(MyScreens.SignUpScreen.route) {
                            inclusive = true
                        }
                    }

                }) { Text("Log In", color = Blue) }

            }


        }

    }


}

@Composable
fun MainTextField(edtValue: String, icon: Int, hint: String, onValueChanges: (String) -> Unit) {

    OutlinedTextField(
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        label = { Text(hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(12.dp),
        shape = ShapeDefaults.Medium,
        leadingIcon = { Icon(painterResource(icon), null) }
    )

}

@Composable
fun PasswordTextField(edtValue: String, icon: Int, hint: String, onValueChanges: (String) -> Unit) {
    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        label = { Text(hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(12.dp),
        shape = ShapeDefaults.Medium,
        leadingIcon = { Icon(painterResource(icon), null) },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {

            val image = if (passwordVisible.value) painterResource(R.drawable.ic_invisible)
            else painterResource(R.drawable.ic_visible)

            Icon(
                painter = image,
                contentDescription = null,
                modifier = Modifier.clickable { passwordVisible.value = !passwordVisible.value }
            )

        }
    )

}

fun clearInputs(viewModel: SignUpViewModel) {
    viewModel.name.value = ""
    viewModel.email.value = ""
    viewModel.password.value = ""
    viewModel.confirmPassword.value = ""
}