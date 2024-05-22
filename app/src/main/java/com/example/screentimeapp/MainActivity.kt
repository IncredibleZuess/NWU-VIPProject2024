/**
 * @author Carlo Barnardo
 * @editor Sebastian Klopper
 *
 * The Code behind the Main application and the required functions
 */

package com.example.screentimeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.screentimeapp.ui.theme.ScreenTimeAppTheme


/**
 * Calls all the required on create functions for the start up of the app
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenTimeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}


/**
 * Edits the text field to greet the user
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

/**
 * The preview for the greeting
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ScreenTimeAppTheme {
        Greeting("Android")
    }
}