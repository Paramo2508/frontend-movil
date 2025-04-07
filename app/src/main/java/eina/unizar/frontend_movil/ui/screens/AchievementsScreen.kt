package eina.unizar.frontend_movil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eina.unizar.frontend_movil.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import eina.unizar.frontend_movil.ui.functions.functions

@Serializable
data class Achievement(
    val id: Int,
    val nombre: String,
    val descripcion: String
)

@Serializable
data class UnachievedResponse(
    val percentage: Double,
    val achievements: List<Achievement>
)

@Composable
fun AchievementItem(
    title: String,
    isCompleted: Boolean = false,
    progress: String = "",
    xpReward: String = ""
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) LightGreenCard else DarkGreenCard
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    color = TextWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                if (progress.isNotEmpty()) {
                    Text(
                        text = progress,
                        color = TextWhite.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (xpReward.isNotEmpty()) {
                    Text(
                        text = xpReward,
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completado",
                        tint = TextWhite,
                        modifier = Modifier
                            .size(24.dp)
                            .background(SuccessGreen, shape = RoundedCornerShape(12.dp))
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AchievementsScreen(userId: String = "1") {

    var achieved by remember { mutableStateOf<List<Achievement>>(emptyList()) }
    var unachieved by remember { mutableStateOf<List<Achievement>>(emptyList()) }
    var progress by remember { mutableStateOf(0.0) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        scope.launch {
            val achievedResponse = functions.get("achievements/$userId")
            val unachievedResponse = functions.get("achievements/unachieved-achievements/$userId")

            // Procesamos la respuesta de logros alcanzados
            achievedResponse?.let {
                try {
                    achieved = Json.decodeFromString(it) // Deserializamos el JSON
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            // Procesamos la respuesta de logros no alcanzados
            unachievedResponse?.let {
                try {
                    val parsed = Json.decodeFromString<UnachievedResponse>(it)
                    unachieved = parsed.achievements
                    progress = parsed.percentage
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }




    /*Row(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleBackground)
            .padding(16.dp)
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Columna izquierda para el título
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "LOGROS",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                modifier = Modifier.padding(vertical = 32.dp)
            )
        }*/

        // UI con los logros obtenidos
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PurpleBackground)
                .padding(16.dp)
        ) {
            // Título
            Text(
                text = "LOGROS",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                modifier = Modifier.padding(vertical = 32.dp)
            )

            // Progreso total de logros
            Text(
                text = "Progreso: ${"%.2f".format(progress)}%",
                color = TextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            // Mostrar los logros completados
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                items(achieved) { achievement ->
                    AchievementItem(
                        title = achievement.nombre,
                        isCompleted = true,
                        xpReward = "+XP"
                    )
                }

                items(unachieved) { achievement ->
                    AchievementItem(
                        title = achievement.nombre,
                        isCompleted = false,
                        progress = "(Progreso)"
                    )
                }
            }
        }
    }
}