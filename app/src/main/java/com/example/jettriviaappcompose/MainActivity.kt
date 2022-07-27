package com.example.jettriviaappcompose

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jettriviaappcompose.screens.TriviaHome
import com.example.jettriviaappcompose.ui.theme.JetTriviaAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
 lateinit   var sharedPreferences:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetTriviaAppComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),

                ) {
                    TriviaHome()
                }
            }
        }
    }
}

fun saveData(score:Int, index:Int){
    //var sharedPreferences = activity?.getSharedPreferences("sharedPref", ComponentActivity.MODE_PRIVATE)
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetTriviaAppComposeTheme {

    }
}