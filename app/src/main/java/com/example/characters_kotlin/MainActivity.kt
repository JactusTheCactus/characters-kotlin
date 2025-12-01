package com.example.characters_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.unit.dp
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

var uni: Map<String, String> = mapOf(
	"acute" to 0x301.toChar().toString(),
	"grave" to 0x300.toChar().toString(),
	"e" to "e${0x323.toChar()}",
	"schwa" to 0x259.toChar().toString(),
	"omega" to 0x3C9.toChar().toString(),
	"ash" to 0xE6.toChar().toString(),
	"ezh" to 0x292.toChar().toString(),
)

class Character(
	var sex: String,
	var name: Array<String>,
	var pron: Array<String>,
	var spec: Set<String>,
	var extr: Set<String>,
)

fun joinName(arr: Array<String>): String {
	return arr.joinToString(" ")
}

fun joinPron(arr: Array<String>): String {
	return arr.joinToString(
		" ",
		transform = { i -> "<$i>" },
	)
}

fun charTitle(sex: String, name: String): String {
	return "$sex $name"
}

class Model : ViewModel() {
	var sex by mutableStateOf(0x2642.toChar().toString())
	var name by mutableStateOf(joinName(arrayOf("John", "Doe")))
	var pron by mutableStateOf(
		joinPron(
			arrayOf(
				"d" + uni["ezh"] + "on",
				"d" + uni["omega"]
			)
		)
	)
	var spec by mutableStateOf(setOf("Human"))
	var extr by mutableStateOf(setOf(""))
		private set

	fun update(c: Character) {
		sex = (when (c.sex) {
			"Male" -> 0x2642
			"Female" -> 0x2640
			else -> 0x26A5
		}).toChar().toString()
		name = joinName(c.name)
		pron = joinPron(c.pron)
		spec = c.spec
		extr = c.extr
	}
}

@Composable
fun Main(
	modifier: Modifier = Modifier,
	model: Model = Model(),
) {
	val characters: MutableMap<String, Character> = mutableMapOf(
		"hound" to Character(
			"Female",
			arrayOf(
				"Hound",
				"NcNamara",
			),
			arrayOf(
				"haund",
				"ny" + uni["acute"] +
					"kn" +
					uni["schwa"] +
					"m" +
					uni["e"] + uni["grave"] +
					"r" +
					uni["schwa"],
			),
			setOf(
				"Human",
				"Changeling",
			),
			setOf(
				"Transforms into a large, black wolf",
			)
		),
		"morrigan" to Character(
			"Female",
			arrayOf(
				"Morrigan",
				"Heffernan",
			),
			arrayOf(
				"m" +
					uni["omega"] + uni["acute"] +
					"r" +
					uni["schwa"] +
					"gy" + uni["grave"] +
					"n",
				"he" + uni["acute"] +
					"f" +
					uni["schwa"] +
					"rn" +
					uni["ash"] + uni["grave"] +
					"n",
			),
			setOf(
				"Reaper",
			),
			setOf(
				"Killing touch",
				"Wields a scythe",
			)
		)
	)
	Column(
		modifier.verticalScroll(rememberScrollState()),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		ElevatedCard(
			modifier.padding(20.dp),
			elevation = CardDefaults.cardElevation(5.dp)
		) {
			Text(
				charTitle(model.sex, model.name),
				style = TextStyle(
					fontSize = 50.sp
				)
			)
			Text(
				model.pron,
				style = TextStyle(
					fontSize = 40.sp
				)
			)
			Text(
				"Species:",
				style = TextStyle(fontSize = 35.sp)
			)
			Column {
				for (e in model.spec) {
					Text(
						"- $e",
						modifier.padding(horizontal = 40.dp),
						style = TextStyle(fontSize = 30.sp)
					)
				}
			}
			if (model.extr.isNotEmpty() && model.extr.first() != "") {
				Text(
					"Extra:",
					style = TextStyle(fontSize = 35.sp)
				)
				Column {
					for (e in model.extr) {
						Text(
							"- $e",
							modifier.padding(horizontal = 40.dp),
							style = TextStyle(fontSize = 30.sp)
						)
					}
				}
			}
		}
		for ((_, c) in characters) {
			Button(
				{
					model.update(c)
				},
				modifier.padding(2.5.dp)
			) {
				Text(
					arrayOf(
						c.name[c.name.size - 1],
						c.name
							.dropLast(1)
							.toTypedArray()
							.joinToString(" ")
					).joinToString(", "),
					modifier,
					style = TextStyle(
						fontSize = 30.sp,
						textAlign = TextAlign.Center
					)
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun Preview() {
	CharacterskotlinTheme { Main() }
}