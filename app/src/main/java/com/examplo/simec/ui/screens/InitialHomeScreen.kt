package com.examplo.simec.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examplo.simec.R

@Composable
fun InitialHomeScreen(onNavigateToCondominios: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.simec_logo),
            contentDescription = "Logo SIMEC",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 24.dp)
        )

        // Texto de boas-vindas
        Text(
            text = "Bem-vindo ao SIMEC",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Botão estilizado
        Button(
            onClick = onNavigateToCondominios,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp)
        ) {
            Text(text = "Ir para Gestão de Condomínios", fontSize = 18.sp)
        }
    }
}
