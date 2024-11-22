package com.examplo.simec.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.examplo.simec.data.models.Condominio
import com.examplo.simec.data.network.SimecApi
import kotlinx.coroutines.launch

@Composable
fun DetalheCondominioScreen(
    api: SimecApi,
    condominioId: Long,
    onBack: () -> Unit
) {
    var condominio by remember { mutableStateOf<Condominio?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(condominioId) {
        coroutineScope.launch {
            try {
                condominio = api.getCondominioById(condominioId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Condomínio") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        condominio?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Nome: ${it.nome}")
                Text(text = "CEP: ${it.cep}")
                Text(text = "Endereço: ${it.endereco}")
                Text(text = "Consumo Mensal: ${it.consumoMensal} kWh")
            }
        }
    }
}
