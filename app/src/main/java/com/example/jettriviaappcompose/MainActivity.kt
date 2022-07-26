package com.example.jettriviaappcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jettriviaappcompose.screens.QuestionsViewModel
import com.example.jettriviaappcompose.ui.theme.JetTriviaAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetTriviaAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
TriviaHome()
                }
            }
        }
    }
}


@Composable
fun TriviaHome(viewModel: QuestionsViewModel = hiltViewModel()){
  Questions(viewModel)
}

@Composable
fun Questions(viewModel: QuestionsViewModel){
   val questions = viewModel.data.value.data?.toMutableList()//to make change in list as it is array list by defalut in model class
    Log.d("SIZE", "Questions:${questions?.size} ")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetTriviaAppComposeTheme {

    }
}