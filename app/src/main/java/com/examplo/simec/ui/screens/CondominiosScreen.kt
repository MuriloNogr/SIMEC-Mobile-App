package com.examplo.simec.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.examplo.simec.data.models.Condominio
import com.examplo.simec.data.network.SimecApi
import com.examplo.simec.data.storage.AsyncStorageHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CondominiosScreen(
    api: SimecApi,
    storageHelper: AsyncStorageHelper,
    onDetails: (Long) -> Unit,
    onAnalyze: (Long) -> Unit,
    onCreate: () -> Unit,
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var condominioList by remember { mutableStateOf<List<Condominio>?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var lastAccessedId by remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                condominioList = api.getCondominios()
                lastAccessedId = storageHelper.getString("last_condominio_id").toLongOrNull()
            } catch (e: Exception) {
                errorMessage = "Erro ao carregar os dados: ${e.message}"
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Condomínios") },
                actions = {
                    IconButton(onClick = onCreate) {
                        Text("Novo")
                    }
                }
            )
        },
        content = { padding ->
            condominioList?.let {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(it) { condominio ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            backgroundColor = if (condominio.id == lastAccessedId) MaterialTheme.colors.primary.copy(alpha = 0.1f) else MaterialTheme.colors.surface
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(text = "Nome: ${condominio.nome}")
                                Text(text = "CEP: ${condominio.cep}")
                                Text(text = "Consumo: ${condominio.consumoMensal} kWh")
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Button(onClick = {
                                        condominio.id?.let {
                                            onAnalyze(it)
                                            coroutineScope.launch {
                                                storageHelper.saveString("last_condominio_id", it.toString())
                                            }
                                        }
                                    }) {
                                        Text(text = "Analisar", color = Color.White)
                                    }
                                    Button(onClick = { condominio.id?.let { onEdit(it) } }) {
                                        Text(text = "Editar", color = Color.White)
                                    }
                                    Button(
                                        onClick = { condominio.id?.let { onDelete(it) } },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = MaterialTheme.colors.error
                                        )
                                    ) {
                                        Text(text = "Excluir", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            } ?: Text(
                text = errorMessage ?: "Carregando...",
                modifier = Modifier.padding(16.dp)
            )
        }
    )
}
