package com.example.jettriviaappcompose.screens


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jettriviaappcompose.model.QuestionItem
import com.example.jettriviaappcompose.util.AppColors

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    val questions =
        viewModel.data.value.data?.toMutableList()
    val questionIndex = remember {
        mutableStateOf(0)
    }
    val scoreState = remember {
        mutableStateOf(0)
    }
    //to make change in list as it is array list by defalut in model class
    if (viewModel.data.value.loading == true) {
        CircularProgressIndicator()

        //   Log.d("Loading", "Questions:Loading.. ")

    } else {
        val question = try {
            questions?.get(questionIndex.value)
        } catch (e: Exception) {
            null
        }
        if (questions != null) {
            QuestionDisplay(question = question!!, questionIndex, viewModel, scoreState) {
                questionIndex.value += 1
            }
        }

    }

}


@Composable
fun QuestionDisplay(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
    viewModel: QuestionsViewModel = hiltViewModel(),
    scoreState: MutableState<Int>,
    onNextClicked: (Int) -> Unit = {}
) {
    val choicesState = remember(question) {
        question.choices.toMutableList()
    }

    /*
    this ceates a dash line10 pixel on 10 pixel off
     */
    val pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
        floatArrayOf(10f, 10f),
        0f
    )
    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }

    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }
//    var scoreState by remember(correctAnswerState) {
//        mutableStateOf(0)
//    }

    val updateAnswer: (Int) -> Unit = remember(question) {

        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }
    androidx.compose.material.Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = AppColors.mDarkPurple

    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            if (questionIndex.value >= 3) ShowProgress(score = questionIndex.value)
            Row(modifier = Modifier.padding(4.dp)) {


                QuestionTracker(counter = questionIndex.value, viewModel.getTotalCount())

                TextViewScore(scoreState.value)

            }

            DrawDottedLine(pathEffect)

            Column {
                Text(
                    text = question.question,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f)//space provided for longer questions 30%
                    , fontSize = 17.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )

                choicesState.forEachIndexed { index, answerText ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp)
                            .height(45.dp)
                            .border(
                                width = 4.dp, brush = Brush.linearGradient(
                                    colors = listOf(
                                        AppColors.mOffDarkPurple,
                                        AppColors.mOffDarkPurple
                                    )
                                ),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clip(
                                RoundedCornerShape(
                                    topStartPercent = 50,
                                    topEndPercent = 50,
                                    bottomEndPercent = 50,
                                    bottomStartPercent = 50
                                )
                            )
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = answerState.value == index,
                            onClick = {
                                updateAnswer(index)
                                if (correctAnswerState.value == true && index == answerState.value) {
                                    scoreState.value += 10
                                } else {
                                    scoreState.value -= 5
                                }
                            },
                            modifier = Modifier.padding(16.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor =
                                if (correctAnswerState.value == true && index == answerState.value) {

                                    Color.Green.copy(alpha = 0.2f)

                                } else {

                                    Color.Red.copy(alpha = 0.2f)
                                }
                            )
                        )

                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    color = if (correctAnswerState.value == true && index == answerState.value) {
                                        Color.Green
                                    } else if (correctAnswerState.value == false && index == answerState.value) {
                                        Color.Red
                                    } else {
                                        AppColors.mOffWhite
                                    }, fontSize = 20.sp
                                )
                            ) {
                                append(answerText)
                            }

                        }
                        Text(text = annotatedString, modifier = Modifier.padding(6.dp))
                    }

                }
                Button(
                    onClick = {
                        onNextClicked(questionIndex.value)

                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppColors.mLightBlue
                    )


                ) {
                    Text(
                        text = "Next",
                        modifier = Modifier.padding(4.dp),
                        color = AppColors.mOffWhite
                    )
                }
            }
        }
    }
}

@Composable
private fun TextViewScore(scoreState: Int) {
    Text(
        text = scoreState.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold,
        color = Color.White, textAlign = TextAlign.End
    )
}

@Composable
fun DrawDottedLine(pathEffect: androidx.compose.ui.graphics.PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            color = Color.Gray,
            start = Offset(0f, 0f),
            end = Offset(size.width, y = 0f),
            pathEffect = pathEffect
        )
    }
}


@Composable
fun QuestionTracker(counter: Int = 0, outOff: Int = 100) {
    //  Row(modifier = Modifier.fillMaxWidth()) {


    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                style = SpanStyle(
                    color = AppColors.mLightGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp
                )
            ) {
                append("Question : $counter/")
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = FontWeight.Light, fontSize = 14.sp
                    )
                ) {
                    append("$outOff")
                }
            }
        }
    }, modifier = Modifier.padding(20.dp))


}


@Composable
fun ShowProgress(score: Int = 50) {

    val gradient = Brush.linearGradient(listOf(Color(0xFFF95077), Color(0xFFBE6BE5)))

//    val progressFactor = remember(score){
//        mutableStateOf(score*0.005f)
//    }
    val progressFactor by remember(score) {
        mutableStateOf(score * 0.005f)
    }
    Row(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(44.dp)
            .border(
                width = 4.dp,
                brush = Brush.radialGradient(
                    colors = listOf(
                        AppColors.mLightPurple,
                        AppColors.mLightPurple
                    )
                ), shape = RoundedCornerShape(34.dp)
            )
            .clip(
                RoundedCornerShape(
                    topEndPercent = 50,
                    topStartPercent = 50,
                    bottomStartPercent = 50,
                    bottomEndPercent = 50
                )
            )
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { /*TODO*/ },
            contentPadding = PaddingValues(1.dp),
            modifier = Modifier
                .fillMaxWidth(progressFactor)
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent
            )
        ) {
            Text(
                text = (score * 10).toString(),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(23.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(
                        6.dp
                    ), color = AppColors.mOffWhite, textAlign = TextAlign.Center
            )
        }
    }
}
