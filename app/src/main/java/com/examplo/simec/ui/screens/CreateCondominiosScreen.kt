package com.examplo.simec.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.examplo.simec.data.models.Condominio
import com.examplo.simec.data.network.SimecApi
import kotlinx.coroutines.launch

@Composable
fun CreateCondominioScreen(
    api: SimecApi,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var nome by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var qtdApartamentos by remember { mutableStateOf("") }
    var consumoMensal by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Criar Condomínio") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        content = { padding ->
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
                            api.saveCondominio(
                                Condominio(
                                    nome = nome,
                                    endereco = endereco,
                                    cep = cep,
                                    qtd_apartamentos = qtdApartamentos.toIntOrNull() ?: 0,
                                    nome_sindico = "Sindico Padrão",
                                    telefone_sindico = "0000-0000",
                                    email_sindico = "email@condominio.com",
                                    descricao = "Condomínio criado via app",
                                    consumoMensal = consumoMensal.toDoubleOrNull() ?: 0.0
                                )
                            )
                            onBack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Salvar")
                }
            }
        }
    )
}
