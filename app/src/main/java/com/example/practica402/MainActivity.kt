package com.example.practica402

import Coche
import Furgoneta
import Moto
import Patinete
import Trailer
import Vehiculo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


import com.example.practica402.ui.theme.Practica402Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practica402Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ConcesionarioApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ConcesionarioApp() {
    var vehiculos by remember { mutableStateOf(mutableListOf<Vehiculo>()) }
    var tipoVehiculo by remember { mutableStateOf("") }
    val ruedas by remember { mutableStateOf(0) }
    val motor by remember { mutableStateOf("") }
    val numAsientos by remember { mutableStateOf(0) }
    val color by remember { mutableStateOf("") }
    val modelo by remember { mutableStateOf("") }
    val cargaMaxima by remember { mutableStateOf(0) }
    var showErrorMessage by remember { mutableStateOf(false) }
    val controlador = LocalSoftwareKeyboardController.current
    var resultadoConsulta by remember { mutableStateOf("") }
    var nombreVehiculo by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    fun guardarVehiculo(
        vehiculos: MutableList<Vehiculo>,
        tipoVehiculo: String,
        ruedas: Int,
        motor: String,
        numAsientos: Int,
        color: String,
        modelo: String,
        cargaMaxima: Int,
        nombreVehiculo: Any?
    ): MutableList<Vehiculo> {

        try {
            when (tipoVehiculo.toLowerCase()) {
                "coche" -> vehiculos.add(
                    Coche(
                        ruedas,
                        motor,
                        numAsientos,
                        color,
                        modelo,
                        nombre = ""
                    )
                )

                "moto" -> vehiculos.add(
                    Moto(
                        ruedas,
                        motor,
                        numAsientos,
                        color,
                        modelo,
                        nombre = ""
                    )
                )

                "patinete" -> vehiculos.add(Patinete(ruedas, motor, color, modelo, nombre = ""))
                "furgoneta" -> vehiculos.add(
                    Furgoneta(
                        ruedas, motor, numAsientos, color, modelo, nombre = ""
                    )
                )

                "trailer" -> vehiculos.add(
                    Trailer(
                        ruedas, motor, numAsientos, color, modelo, nombre = ""
                    )
                )

                else -> showErrorMessage = true
            }
        } catch (e: Exception) {
            showErrorMessage = true
        }
        return vehiculos
    }

    fun consultarNumeroDeVehiculos(vehiculos: List<Vehiculo>): String {
        // Lógica para contar el número de vehículos por tipo
        var contadorCoches = 0
        var contadorMotos = 0
        var contadorPatinetes = 0
        var contadorFurgonetas = 0
        var contadorTrailers = 0

        for (vehiculo in vehiculos) {
            when (vehiculo) {
                is Coche -> contadorCoches++
                is Moto -> contadorMotos++
                is Patinete -> contadorPatinetes++
                is Furgoneta -> contadorFurgonetas++
                is Trailer -> contadorTrailers++
            }
        }

        return """
        Número de vehículos creados:
        - Coches: $contadorCoches
        - Motos: $contadorMotos
        - Patinetes: $contadorPatinetes
        - Furgonetas: $contadorFurgonetas
        - Tráileres: $contadorTrailers
    """.trimIndent()
    }

    fun listarVehiculosPorNombre(vehiculos: List<Vehiculo>, nombre: String) {
        println("Lista de vehículos con nombre '$nombre':")

        for (vehiculo in vehiculos) {
            if (vehiculo.nombre.equals(nombre, ignoreCase = true)) {
                val tipo = when (vehiculo) {
                    is Coche -> "Coche"
                    is Moto -> "Moto"
                    // ... (resto de las opciones)
                    else -> "Desconocido"
                }

                println("Tipo: $tipo, Modelo: ${vehiculo.modelo}")
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 400.dp)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = nombreVehiculo,
                onValueChange = {
                    nombreVehiculo = it
                },
                label = { Text(text = "Nombre del Vehículo") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(
                    onDone = {
                        vehiculos = guardarVehiculo(
                            vehiculos,
                            tipoVehiculo,
                            ruedas,
                            motor,
                            numAsientos,
                            color,
                            modelo,
                            cargaMaxima,
                            nombreVehiculo
                        )
                        controlador?.hide()
                    }
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    listarVehiculosPorNombre(vehiculos, nombreVehiculo)
                }
            ) {
                Text("Listado de Vehículos por Nombre")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            TextField(value = tipoVehiculo,
                onValueChange = {
                    tipoVehiculo = it
                },
                label = { Text(text = "Tipo de Vehículo") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone = {
                    vehiculos = guardarVehiculo(
                        vehiculos,
                        tipoVehiculo,
                        ruedas,
                        motor,
                        numAsientos,
                        color,
                        modelo,
                        cargaMaxima,
                        nombreVehiculo
                    )
                    controlador?.hide()
                }))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                vehiculos = guardarVehiculo(
                    vehiculos,
                    tipoVehiculo,
                    ruedas,
                    motor,
                    numAsientos,
                    color,
                    modelo,
                    cargaMaxima,
                    nombreVehiculo
                )
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Text("Añadir Vehículo")
            }

            Spacer(modifier = Modifier.width(16.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                resultadoConsulta = consultarNumeroDeVehiculos(vehiculos)
                showErrorMessage = false
            }) {
                Icon(imageVector = Icons.Default.List, contentDescription = null)
                Text("Consultar Número de Vehículos")
            }
        }


        // Etiqueta para mostrar el resultado de la consulta
        if (resultadoConsulta.isNotBlank()) {
            Text(resultadoConsulta, modifier = Modifier.padding(8.dp))
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Otros controles e información sobre los vehículos...

        Spacer(modifier = Modifier.height(16.dp))

        if (showErrorMessage) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Default.Warning, contentDescription = null, tint = Color.Red
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Por favor, introduce información válida.")
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    Practica402Theme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            ConcesionarioApp()
        }
    }
}



