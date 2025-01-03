package com.example.gabistore.ui

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gabistore.di.myModules
import com.example.gabistore.model.repository.TokenInMemory
import com.example.gabistore.model.repository.user.UserRepository
import com.example.gabistore.ui.features.IntroScreen
import com.example.gabistore.ui.features.cart.CartScreen
import com.example.gabistore.ui.features.category.CategoryScreen
import com.example.gabistore.ui.features.main.MainScreen
import com.example.gabistore.ui.features.product.ProductScreen
import com.example.gabistore.ui.features.profile.ProfileScreen
import com.example.gabistore.ui.features.signIn.SignInScreen
import com.example.gabistore.ui.features.signUp.SignUpScreen
import com.example.gabistore.ui.theme.MainAppTheme
import com.example.gabistore.util.KEY_CATEGORY_ARG
import com.example.gabistore.util.KEY_PRODUCT_ARG
import com.example.gabistore.util.MyScreens
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.get
import dev.burnoo.cokoin.navigation.KoinNavHost
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        enableEdgeToEdge()
        setContent {

            Koin(appDeclaration = {

                androidContext(this@MainActivity)
                modules(myModules)

            }) {
                MainAppTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                        val userRepository: UserRepository = get()
                        userRepository.loadToken()

                        GabiStoreUi(
                            Modifier.padding(innerPadding)
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun GabiStoreUi(modifier: Modifier) {
    val navController = rememberNavController()
    KoinNavHost(
        navController = navController,
        startDestination = MyScreens.MainScreen.route
    ) {

        composable(MyScreens.MainScreen.route) {

            if (TokenInMemory.token != null) {
                MainScreen()
            } else {
                IntroScreen()
            }

        }
        composable(
            route = MyScreens.ProductScreen.route + "/" + "{$KEY_PRODUCT_ARG}",
            arguments = listOf(navArgument(KEY_PRODUCT_ARG) { type = NavType.StringType })
        ) {
            ProductScreen(it.arguments!!.getString(KEY_PRODUCT_ARG, "null")) // id product needed
        }
        composable(
            route = MyScreens.CategoryScreen.route + "/" + "{$KEY_CATEGORY_ARG}",
            arguments = listOf(navArgument(KEY_CATEGORY_ARG) { type = NavType.StringType })
        ) {
            CategoryScreen(
                it.arguments!!.getString(
                    KEY_CATEGORY_ARG,
                    "null"
                )
            ) // category needed
        }
        composable(MyScreens.ProfileScreen.route) {
            ProfileScreen()
        }
        composable(MyScreens.CartScreen.route) {
            CartScreen()
        }
        composable(MyScreens.SignUpScreen.route) {
            SignUpScreen()
        }
        composable(MyScreens.SignInScreen.route) {
            SignInScreen()
        }

    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            GabiStoreUi(
                Modifier.padding(innerPadding)
            )
        }
    }
}