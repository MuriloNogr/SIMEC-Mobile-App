package com.examplo.simec.data.models

data class Condominio(
    val id: Long? = null,
    val nome: String,
    val endereco: String,
    val cep: String,
    val qtd_apartamentos: Int,
    val nome_sindico: String,
    val telefone_sindico: String,
    val email_sindico: String,
    val descricao: String,
    val consumoMensal: Double
)
