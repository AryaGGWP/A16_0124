package com.example.finalprojectpam.repository

import com.example.finalprojectpam.model.AllAsetResponse
import com.example.finalprojectpam.model.AllKategoriResponse
import com.example.finalprojectpam.model.AllPendapatanResponse
import com.example.finalprojectpam.model.AllPengeluaranResponse
import com.example.finalprojectpam.model.Aset
import com.example.finalprojectpam.model.Kategori
import com.example.finalprojectpam.model.ManajemenKeuanganResponse
import com.example.finalprojectpam.model.Pendapatan
import com.example.finalprojectpam.model.Pengeluaran
import com.example.finalprojectpam.service.KeuanganService
import java.io.IOException

interface KeuanganRepository{
    //aset
    suspend fun insertAset(aset: Aset)
    suspend fun getAllAset(): AllAsetResponse
    suspend fun getAsetById(idAset: Int): Aset
    suspend fun updateAset(idAset: Int, aset: Aset)
    suspend fun deleteAset(idAset: Int)

    //kategori
    suspend fun insertKategori(kategori: Kategori)
    suspend fun getAllKategori(): AllKategoriResponse
    suspend fun getKategoriById(idKategori: Int): Kategori
    suspend fun updateKategori(idKategori: Int, kategori: Kategori)
    suspend fun deleteKategori(idKategori: Int)

    //pendapatan
    suspend fun insertPendapatan(pendapatan: Pendapatan)
    suspend fun getAllPendapatan(): AllPendapatanResponse
    suspend fun getPendapatanById(idPendapatan: Int): Pendapatan
    suspend fun updatePendapatan(idPendapatan: Int, pendapatan: Pendapatan)
    suspend fun deletePendapatan(idPendapatan: Int)

    //pengeluaran
    suspend fun insertPengeluaran(pengeluaran: Pengeluaran)
    suspend fun getAllPengeluaran(): AllPengeluaranResponse
    suspend fun getPengeluaranById(idPengeluaran: Int): Pengeluaran
    suspend fun updatePengeluaran(idPengeluaran: Int, pengeluaran: Pengeluaran)
    suspend fun deletePengeluaran(idPengeluaran: Int)

    //Manajemen Keuangan
    suspend fun getSaldo(): ManajemenKeuanganResponse
}

class NetworkKontakRepository(
    private val kontakApiService: KeuanganService)
    : KeuanganRepository
{
    // Aset
    override suspend fun insertAset(aset: Aset) {
        return kontakApiService.insertAset(aset)
    }
    override suspend fun getAllAset(): AllAsetResponse {
        return kontakApiService.getAllAset()
    }
    override suspend fun getAsetById(idAset: Int): Aset {
        return kontakApiService.getAsetById(idAset).data
    }
    override suspend fun updateAset(idAset: Int, aset: Aset) {
        return kontakApiService.updateAset(idAset, aset)
    }
    override suspend fun deleteAset(idAset: Int) {
        try {
            val response = kontakApiService.deleteAset(idAset)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete kontak. HTTP Status code: " +
                        "${response.code()}")
            }
            else{
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

    // Kategori
    override suspend fun insertKategori(kategori: Kategori) {
        return kontakApiService.insertKategori(kategori)
    }
    override suspend fun getAllKategori(): AllKategoriResponse {
        return kontakApiService.getAllKategori()
    }
    override suspend fun getKategoriById(idKategori: Int): Kategori {
        return kontakApiService.getKategoriById(idKategori).data
    }
    override suspend fun updateKategori(idKategori: Int, kategori: Kategori) {
        return kontakApiService.updateKategori(idKategori, kategori)
    }
    override suspend fun deleteKategori(idKategori: Int) {
        try {
            val response = kontakApiService.deleteKategori(idKategori)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete kontak. HTTP Status code: " +
                        "${response.code()}")
            }
            else{
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

    // Pendapatan
    override suspend fun insertPendapatan(pendapatan: Pendapatan) {
        return kontakApiService.insertPendapatan(pendapatan)
    }
    override suspend fun getAllPendapatan(): AllPendapatanResponse {
        return kontakApiService.getAllPendapatan()
    }
    override suspend fun getPendapatanById(idPendapatan: Int): Pendapatan {
        return kontakApiService.getPendapatanById(idPendapatan).data
    }
    override suspend fun updatePendapatan(idPendapatan: Int, pendapatan: Pendapatan) {
        return kontakApiService.updatePendapatan(idPendapatan, pendapatan)
    }
    override suspend fun deletePendapatan(idPendapatan: Int) {
        try {
            val response = kontakApiService.deletePendapatan(idPendapatan)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete kontak. HTTP Status code: " +
                        "${response.code()}")
            }
            else{
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

    // Pengeluaran
    override suspend fun insertPengeluaran(pengeluaran: Pengeluaran) {
        return kontakApiService.insertPengeluaran(pengeluaran)
    }
    override suspend fun getAllPengeluaran(): AllPengeluaranResponse {
        return kontakApiService.getAllPengeluaran()
    }
    override suspend fun getPengeluaranById(idPengeluaran: Int): Pengeluaran {
        return kontakApiService.getPengeluaranById(idPengeluaran).data
    }
    override suspend fun updatePengeluaran(idPengeluaran: Int, pengeluaran: Pengeluaran) {
        return kontakApiService.updatePengeluaran(idPengeluaran, pengeluaran)
    }
    override suspend fun deletePengeluaran(idPengeluaran: Int) {
        try {
            val response = kontakApiService.deletePengeluaran(idPengeluaran)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete kontak. HTTP Status code: " +
                        "${response.code()}")
            }
            else{
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }
    //saldo
    override suspend fun getSaldo(): ManajemenKeuanganResponse {
        val response = kontakApiService.getSaldo()
        if (response.isSuccessful) {
            println("Response body: ${response.body()}")
            return response.body() ?: throw Exception("Response body is null")
        } else {
            throw Exception("Error fetching data: ${response.code()} - ${response.message()}")
        }
    }
}