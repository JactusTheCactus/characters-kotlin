package com.example.characters_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.characters_kotlin.ui.theme.CharacterskotlinTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			CharacterskotlinTheme {
				Scaffold(
					Modifier
						.fillMaxSize()
						.statusBarsPadding()
				) { innerPadding ->
					Main(Modifier.padding(innerPadding))
				}
			}
		}
	}
}

class Character(var name: Array<String>)
class Model : ViewModel() {
	var name by mutableStateOf("John Doe")
		private set

	fun update(n: String? = null) {
		if (n != null) {
			name = n
		}
	}
}

@Composable
fun Main(
	modifier: Modifier = Modifier,
	model: Model = Model(),
) {
	val characters: MutableMap<String, Character> = mutableMapOf(
		"hound" to Character(
			arrayOf("Hound", "NcNamara")
		),
		"morrigan" to Character(
			arrayOf("Morrigan", "Heffernan")
		)
	)
	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		Card {
			Text(
				text = model.name,
				style = TextStyle(
					fontSize = 30.sp
				)
			)
		}
		Column {
			for ((_, c) in characters) {
				Button(onClick = {
					model.update(
						c.name.joinToString(" ")
					)
				}) {
					Text(
						text = arrayOf(
							c.name[c.name.size - 1],
							c.name
								.dropLast(1)
								.toTypedArray()
								.joinToString(" ")
						).joinToString(", "),
						modifier = modifier,
						style = TextStyle(
							fontSize = 30.sp,
							textAlign = TextAlign.Center
						)
					)
				}
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun Preview() {
	CharacterskotlinTheme {
		Main()
	}
}