package com.example.pruebacompose

import android.content.res.Configuration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pruebacompose.ui.theme.PruebaComposeTheme

private val messages: List<MyMensajes> = listOf(
    MyMensajes("hola 1", "mundo"),
    MyMensajes("hola 2", "mundo"),
    MyMensajes("hola 3", "mundo"),
    MyMensajes("hola 4", "mundo")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaComposeTheme() {
                myMensajes(messages)
            }

        }
    }
}

@Composable
fun misComponentes(messages: MyMensajes) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        myImage()
        myTexts(messages)
    }


}

@Composable
fun myTexts(messages: MyMensajes) {
    Column(modifier = Modifier.padding(3.dp)) {
        myText(
            messages.title,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        myText(
            messages.body,
            MaterialTheme.colorScheme.onBackground,
            MaterialTheme.typography.bodySmall
        )
    }

}

@Composable
fun myImage() {
    Image(
        painterResource(R.drawable.ic_launcher_foreground),
        "Imagen a mostrar",
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Green)
            .size(64.dp)
    )
}

@Composable
fun myText(text: String, color: Color, style: TextStyle) {
    Text(text, color = color, style = style)
}

data class MyMensajes(val title: String, val body: String)

@Composable
fun myMensajes(messages: List<MyMensajes>) {
    LazyColumn {
        items(messages) { messages ->
            misComponentes(messages)
        }
    }

}


@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    PruebaComposeTheme() {
        myMensajes(messages)
    }
}