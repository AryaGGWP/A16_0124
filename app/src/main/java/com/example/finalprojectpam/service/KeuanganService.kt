package com.example.finalprojectpam.service

import com.example.finalprojectpam.model.AllAsetResponse
import com.example.finalprojectpam.model.AllKategoriResponse
import com.example.finalprojectpam.model.AllPendapatanResponse
import com.example.finalprojectpam.model.AllPengeluaranResponse
import com.example.finalprojectpam.model.Aset
import com.example.finalprojectpam.model.AsetDetail
import com.example.finalprojectpam.model.Kategori
import com.example.finalprojectpam.model.KategoriDetail
import com.example.finalprojectpam.model.ManajemenKeuanganResponse
import com.example.finalprojectpam.model.Pendapatan
import com.example.finalprojectpam.model.PendapatanDetail
import com.example.finalprojectpam.model.Pengeluaran
import com.example.finalprojectpam.model.PengeluaranDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KeuanganService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    // Aset
    @POST("aset/store")
    suspend fun insertAset(@Body aset: Aset)

    @GET(".")
    suspend fun getAllAset(): AllAsetResponse

    @GET("aset/{id_aset}")
    suspend fun getAsetById(@Path("id_aset") idAset: Int): AsetDetail

    @PUT("aset/{id_aset}")
    suspend fun updateAset(@Path("id_aset") idAset: Int, @Body aset: Aset)

    @DELETE("aset/{id_aset}")
    suspend fun deleteAset(@Path("id_aset") idAset: Int): Response<Void>

    // Kategori
    @POST("kategori/store")
    suspend fun insertKategori(@Body kategori: Kategori)

    @GET("kategori")
    suspend fun getAllKategori(): AllKategoriResponse

    @GET("kategori/{id_kategori}")
    suspend fun getKategoriById(@Path("id_kategori") idKategori: Int): KategoriDetail

    @PUT("kategori/{id_kategori}")
    suspend fun updateKategori(@Path("id_kategori") idKategori: Int, @Body kategori: Kategori)

    @DELETE("kategori/{id_kategori}")
    suspend fun deleteKategori(@Path("id_kategori") id: Int): Response<Void>

    // Pendapatan
    @POST("pendapatan/store")
    suspend fun insertPendapatan(@Body pendapatan: Pendapatan)

    @GET("pendapatan")
    suspend fun getAllPendapatan(): AllPendapatanResponse

    @GET("pendapatan/{id_pendapatan}")
    suspend fun getPendapatanById(@Path("id_pendapatan") idPendapatan: Int): PendapatanDetail

    @PUT("pendapatan/{id_pendapatan}")
    suspend fun updatePendapatan(@Path("id_pendapatan") idPendapatan: Int, @Body pendapatan: Pendapatan)

    @DELETE("pendapatan/{id_pendapatan}")
    suspend fun deletePendapatan(@Path("id_pendapatan") idPendapatan: Int): Response<Void>

    // Pengeluaran
    @POST("pengeluaran/store")
    suspend fun insertPengeluaran(@Body pengeluaran: Pengeluaran)

    @GET("pengeluaran")
    suspend fun getAllPengeluaran(): AllPengeluaranResponse

    @GET("pengeluaran/{id_pengeluaran}")
    suspend fun getPengeluaranById(@Path("id_pengeluaran") idPengeluaran: Int): PengeluaranDetail

    @PUT("pengeluaran/{id_pengeluaran}")
    suspend fun updatePengeluaran(@Path("id_pengeluaran") idPengeluaran: Int, @Body pengeluaran: Pengeluaran)

    @DELETE("pengeluaran/{id_pengeluaran}")
    suspend fun deletePengeluaran(@Path("id_pengeluaran") idPengeluaran: Int): Response<Void>

    // Manajemen Keuangan
    @GET("saldo")
    suspend fun getSaldo(): Response<ManajemenKeuanganResponse>
}