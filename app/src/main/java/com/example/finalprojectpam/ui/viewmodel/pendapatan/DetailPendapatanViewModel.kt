package com.example.finalprojectpam.ui.viewmodel.pendapatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.launch

class DetailPendapatanViewModel(private val pendapatanRepository: KeuanganRepository) : ViewModel() {
    // State
    var uiState by mutableStateOf(DetailPendapatanUiState())
        private set

    // Fungsi untuk load detail pendapatan berdasarkan ID
    fun loadDetailPendapatan(idPendapatan: Int) {
        viewModelScope.launch {
            try {
                val pendapatan = pendapatanRepository.getPendapatanById(idPendapatan)
                val asetList = pendapatanRepository.getAllAset().data // Load aset list
                val kategoriList = pendapatanRepository.getAllKategori().data // Load kategori list

                val namaAset = asetList.find { it.idAset == pendapatan.idAset }?.namaAset.orEmpty()
                val namaKategori = kategoriList.find { it.idKategori == pendapatan.idKategori }?.namaKategori.orEmpty()

                uiState = uiState.copy(
                    idPendapatan = pendapatan.idPendapatan,
                    idAset = pendapatan.idAset,
                    idKategori = pendapatan.idKategori,
                    namaAset = namaAset,
                    namaKategori = namaKategori,
                    tanggalTransaksi = pendapatan.tanggalTransaksi,
                    total = pendapatan.total,
                    catatan = pendapatan.catatan,
                    isLoading = false,
                    isError = false
                )
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Gagal memuat data pendapatan"
                )
            }
        }
    }

    // Fungsi untuk menghapus pendapatan
    fun deletePendapatan(idPendapatan: Int) {
        viewModelScope.launch {
            try {
                pendapatanRepository.deletePendapatan(idPendapatan)
                uiState = uiState.copy(deleted = true)
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(errorMessage = "Gagal menghapus data pendapatan")
            }
        }
    }
}

// Data class untuk UI State
data class DetailPendapatanUiState(
    val idPendapatan: Int = 0,
    val idAset: Int = 0,
    val idKategori: Int = 0,
    val namaAset: String = "", // Nama aset dari idAset
    val namaKategori: String = "", // Nama kategori dari idKategori
    val tanggalTransaksi: String = "",
    val total: Double = 0.0,
    val catatan: String = "",
    val isLoading: Boolean = true,
    val isError : Boolean = false, // Untuk loading state
    val deleted: Boolean = false, // Untuk indikasi apakah data berhasil dihapus
    val errorMessage: String? = null // Untuk pesan error
)

