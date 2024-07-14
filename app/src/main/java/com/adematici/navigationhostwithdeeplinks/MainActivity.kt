package com.adematici.navigationhostwithdeeplinks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.adematici.navigationhostwithdeeplinks.ui.theme.NavigationHostWithDeeplinksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationHostWithDeeplinksTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    NavHostSetup()
                }
            }
        }
    }
}

@Composable
fun NavHostSetup() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }

        composable(
            route = "profile/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink { uriPattern = "https://www.example.com/profile/{userId}" })
        ) { backStackEntry ->
            ProfileScreen(userId = backStackEntry.arguments?.getString("userId"))
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Button(onClick = { navController.navigate("profile/123") }) {
        Text(text = "Go to Profile")
    }
}

@Composable
fun ProfileScreen(userId: String?) {
    Text(text = "User ID: $userId")
}
