package com.examplo.simec.data.network

import com.examplo.simec.data.models.Condominio
import okhttp3.ResponseBody
import retrofit2.http.*

interface SimecApi {
    @GET("api/condominios")
    suspend fun getCondominios(): List<Condominio>

    @GET("api/condominios/{id}")
    suspend fun getCondominioById(@Path("id") id: Long): Condominio

    @POST("api/condominios")
    suspend fun saveCondominio(@Body condominio: Condominio): Condominio

    @DELETE("api/condominios/{id}")
    suspend fun deleteCondominio(@Path("id") id: Long)

    @GET("api/openai/analisar-consumo/{id}")
    suspend fun analyzeConsumption(@Path("id") id: Long): ResponseBody
}
