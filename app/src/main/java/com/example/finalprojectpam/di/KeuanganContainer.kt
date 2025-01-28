package com.example.finalprojectpam.di

import com.example.finalprojectpam.repository.KeuanganRepository
import com.example.finalprojectpam.repository.NetworkKontakRepository
import com.example.finalprojectpam.service.KeuanganService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val kontakRepository: KeuanganRepository
}

class KeuanganContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:4001/api/keuangan/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val keuanganService: KeuanganService by lazy {
        retrofit.create(KeuanganService::class.java) }

    override val kontakRepository: KeuanganRepository by lazy {
        NetworkKontakRepository(keuanganService) }
}