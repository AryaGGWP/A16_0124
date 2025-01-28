package com.example.finalprojectpam.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.launch

class DetailPengeluaranViewModel(private val pengeluaranRepository: KeuanganRepository) : ViewModel() {
    // State
    var uiState by mutableStateOf(DetailPengeluaranUiState())
        private set

    // Fungsi untuk load detail pengeluaran berdasarkan ID
    fun loadDetailPengeluaran(idPengeluaran: Int) {
        viewModelScope.launch {
            try {
                val pengeluaran = pengeluaranRepository.getPengeluaranById(idPengeluaran)
                val asetList = pengeluaranRepository.getAllAset().data // Load aset list
                val kategoriList = pengeluaranRepository.getAllKategori().data // Load kategori list

                val namaAset = asetList.find { it.idAset == pengeluaran.idAset }?.namaAset.orEmpty()
                val namaKategori = kategoriList.find { it.idKategori == pengeluaran.idKategori }?.namaKategori.orEmpty()

                uiState = uiState.copy(
                    idPengeluaran = pengeluaran.idPengeluaran,
                    idAset = pengeluaran.idAset,
                    idKategori = pengeluaran.idKategori,
                    namaAset = namaAset,
                    namaKategori = namaKategori,
                    tanggalTransaksi = pengeluaran.tanggalTransaksi,
                    total = pengeluaran.total,
                    catatan = pengeluaran.catatan,
                    isLoading = false,
                    isError = false
                )
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Gagal memuat data pengeluaran"
                )
            }
        }
    }

    // Fungsi untuk menghapus pengeluaran
    fun deletePengeluaran(idPengeluaran: Int) {
        viewModelScope.launch {
            try {
                pengeluaranRepository.deletePengeluaran(idPengeluaran)
                uiState = uiState.copy(deleted = true)
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(errorMessage = "Gagal menghapus data pengeluaran")
            }
        }
    }
}

// Data class untuk UI State
data class DetailPengeluaranUiState(
    val idPengeluaran: Int = 0,
    val idAset: Int = 0,
    val idKategori: Int = 0,
    val namaAset: String = "", // Nama aset dari idAset
    val namaKategori: String = "", // Nama kategori dari idKategori
    val tanggalTransaksi: String = "",
    val total: Double = 0.0,
    val catatan: String = "",
    val isLoading: Boolean = true,
    val isError: Boolean = false, // Untuk loading state
    val deleted: Boolean = false, // Untuk indikasi apakah data berhasil dihapus
    val errorMessage: String? = null // Untuk pesan error
)

