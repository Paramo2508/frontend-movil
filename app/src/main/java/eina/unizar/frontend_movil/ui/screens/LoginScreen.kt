package eina.unizar.frontend_movil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import eina.unizar.frontend_movil.ui.theme.PurpleBackground
import eina.unizar.frontend_movil.ui.theme.TextWhite
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleBackground)
            .padding(16.dp)
    ) {
        // Usamos un Column envuelto en un verticalScroll para que los elementos sean desplazables
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()), // Permite el desplazamiento
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Iniciar Sesión",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Tarjeta que contiene los campos de texto y el botón
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f) // La tarjeta ocupa el 85% del ancho de la pantalla
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1D1B2A).copy(alpha = 0.2f)),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Campo de Correo Electrónico
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo Electrónico") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,    // Texto en blanco cuando está seleccionado
                            unfocusedTextColor = Color.White, // Texto en blanco siempre
                            cursorColor = Color.White,        // Cursor en blanco
                            focusedBorderColor = Color.White, // Borde al seleccionar
                            unfocusedBorderColor = Color.Gray // Borde normal
                        )
                    )

                    // Campo de Contraseña
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,    // Texto en blanco cuando está seleccionado
                            unfocusedTextColor = Color.White, // Texto en blanco siempre
                            cursorColor = Color.White,        // Cursor en blanco
                            focusedBorderColor = Color.White, // Borde al seleccionar
                            unfocusedBorderColor = Color.Gray // Borde normal
                        )
                    )

                    // Botón de Iniciar Sesión
                    Button(
                        onClick = { /* TODO: Implementar lógica de inicio de sesión */ },
                        modifier = Modifier
                            .fillMaxWidth()  // El botón ocupa todo el ancho de la tarjeta
                            .height(50.dp)   // Ajustamos la altura del botón
                            .padding(top = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F37C9)) // Color de fondo
                    ) {
                        Text(
                            text = "Entrar",
                            color = TextWhite,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            Text(
                text = "No tengo cuenta",
                fontSize = 14.sp,
                color = TextWhite.copy(alpha = 0.7f),
                modifier = Modifier
                    .clickable {
                        // Navegar a la pantalla de nueva cuenta
                        navController.navigate("new_account")
                    }
            )
            // Link o texto para "Olvidé mi contraseña"
            Text(
                text = "¿Olvidaste tu contraseña?",
                fontSize = 14.sp,
                color = TextWhite.copy(alpha = 0.7f),
                modifier = Modifier.clickable {
                    // Implementa la acción para recuperar la contraseña
                }
            )
        }
    }
}