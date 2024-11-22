package com.examplo.simec.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.examplo.simec.data.models.Condominio
import com.examplo.simec.data.network.SimecApi
import kotlinx.coroutines.launch

@Composable
fun EditCondominioScreen(
    api: SimecApi,
    condominioId: Long,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var condominio by remember { mutableStateOf<Condominio?>(null) }
    var nome by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var qtdApartamentos by remember { mutableStateOf("") }
    var consumoMensal by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(condominioId) {
        coroutineScope.launch {
            try {
                condominio = api.getCondominioById(condominioId)
                condominio?.let {
                    nome = it.nome
                    endereco = it.endereco
                    cep = it.cep
                    qtdApartamentos = it.qtd_apartamentos.toString()
                    consumoMensal = it.consumoMensal.toString()
                }
            } catch (e: Exception) {
                errorMessage = "Erro ao carregar condomínio: ${e.message}"
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Condomínio") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        content = { padding ->
            if (condominio != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome") }
                    )
                    OutlinedTextField(
                        value = endereco,
                        onValueChange = { endereco = it },
                        label = { Text("Endereço") }
                    )
                    OutlinedTextField(
                        value = cep,
                        onValueChange = { cep = it },
                        label = { Text("CEP") }
                    )
                    OutlinedTextField(
                        value = qtdApartamentos,
                        onValueChange = { qtdApartamentos = it },
                        label = { Text("Quantidade de Apartamentos") }
                    )
                    OutlinedTextField(
                        value = consumoMensal,
                        onValueChange = { consumoMensal = it },
                        label = { Text("Consumo Mensal (kWh)") }
                    )
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                try {
                                    api.saveCondominio(
                                        Condominio(
                                            id = condominioId,
                                            nome = nome,
                                            endereco = endereco,
                                            cep = cep,
                                            qtd_apartamentos = qtdApartamentos.toIntOrNull() ?: 0,
                                            nome_sindico = condominio!!.nome_sindico,
                                            telefone_sindico = condominio!!.telefone_sindico,
                                            email_sindico = condominio!!.email_sindico,
                                            descricao = condominio!!.descricao,
                                            consumoMensal = consumoMensal.toDoubleOrNull() ?: 0.0
                                        )
                                    )
                                    onBack()
                                } catch (e: Exception) {
                                    errorMessage = "Erro ao salvar alterações: ${e.message}"
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Salvar Alterações")
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = errorMessage ?: "Carregando condomínio...")
                }
            }
        }
    )
}
