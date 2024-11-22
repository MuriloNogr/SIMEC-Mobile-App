package com.examplo.simec.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.examplo.simec.data.network.SimecApi
import okhttp3.ResponseBody

@Composable
fun AnalyzeConsumptionScreen(
    condominioId: Long,
    api: SimecApi,
    onBack: () -> Unit
) {
    val consumptionResult = remember { mutableStateOf("Carregando análise...") }

    LaunchedEffect(condominioId) {
        try {
            val responseBody: ResponseBody = api.analyzeConsumption(condominioId)
            val result = responseBody.string()
            consumptionResult.value = result
        } catch (e: Exception) {
            consumptionResult.value = "Erro ao carregar análise: ${e.localizedMessage}"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Análise de Consumo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(text = consumptionResult.value)
            }
        }
    )
}
