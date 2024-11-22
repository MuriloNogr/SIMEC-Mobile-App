package com.examplo.simec.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.examplo.simec.data.models.Condominio
import com.examplo.simec.data.network.SimecApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    api: SimecApi,
    onNavigateToDetails: (Long) -> Unit,
    onNavigateToAnalysis: (Long) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var condominioList by remember { mutableStateOf<List<Condominio>?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                condominioList = api.getCondominios()
            } catch (e: Exception) {
                errorMessage = "Erro ao carregar os dados: ${e.message}"
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "SIMEC - Gestão de Condomínios") }
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier.padding(contentPadding)
            ) {
                if (condominioList != null) {
                    condominioList!!.forEach { condominio ->
                        condominio.id?.let { id ->
                            Card(
                                modifier = Modifier.padding(8.dp),
                                onClick = { onNavigateToDetails(id) }
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(text = "Nome: ${condominio.nome}")
                                    Text(text = "CEP: ${condominio.cep}")
                                    Text(text = "Consumo: ${condominio.consumoMensal} kWh")
                                    Button(
                                        onClick = { onNavigateToAnalysis(id) },
                                        modifier = Modifier.padding(top = 8.dp)
                                    ) {
                                        Text(text = "Analisar Consumo")
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Text(text = errorMessage ?: "Carregando...")
                }
            }
        }
    )
}
