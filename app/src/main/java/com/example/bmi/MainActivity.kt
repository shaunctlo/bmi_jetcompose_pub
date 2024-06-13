package com.example.bmi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bmi.ui.theme.BMITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMITheme {
                Surface {
                    MainActivityContent()
                }
            }
        }
    }
}

@Composable
fun EnterHeight(height: String, changed: (String) -> Unit) {
    TextField(
        value = height,
        label = { Text("Enter your height in meter") },
        onValueChange = changed,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun EnterWeight(weight: String, changed: (String) -> Unit) {
    TextField(
        value = weight,
        label = { Text("Enter your weight in KG") },
        onValueChange = changed,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CalculateButton(clicked: () -> Unit) {
    Button(onClick = clicked) {
        Text("Calculate")
    }
}

@Composable
fun BMIText(bmi: String) {
    if (bmi == "") return
    val formatBmi = "%.2f".format(bmi.toFloat())
    Text("BMI: $formatBmi")
}

@Composable
fun MainActivityContent() {
    val height = remember { mutableStateOf("") }
    val weight = remember { mutableStateOf("") }
    val bmi = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        EnterHeight(height.value) { height.value = it }
        EnterWeight(weight.value) { weight.value = it }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CalculateButton {
                height.value.toFloatOrNull()?.let {
                    weight.value.toFloatOrNull()?.let {
                        bmi.value =
                            (weight.value.toFloat() / (height.value.toFloat() * height.value.toFloat())).toString()
                    }
                }
            }
        }
        BMIText(bmi.value)
    }
}

@Preview(showBackground = true)
@Composable
fun BMIPreview() {
    BMITheme {
        Surface {
            MainActivityContent()
        }
    }
}