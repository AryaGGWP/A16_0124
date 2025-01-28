package com.example.finalprojectpam.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Kategori
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.launch

class DetailKategoriViewModel(private val keuanganRepository: KeuanganRepository) : ViewModel() {
    var uiState by mutableStateOf(DetailKategoriUiState())
        private set

    fun getKategoriById(idKategori: Int) {
        viewModelScope.launch {
            try {
                val kategori = keuanganRepository.getKategoriById(idKategori)
                uiState = DetailKategoriUiState(
                    kategori = kategori,
                    isLoading = false,
                    isError = false
                )
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(
                    isLoading = false,
                    isError = true
                )
            }
        }
    }

    // Fungsi untuk menghapus kategori berdasarkan id
    fun deleteKategori(idKategori: Int) {
        viewModelScope.launch {
            try {
                keuanganRepository.deleteKategori(idKategori)
                uiState = uiState.copy(
                    kategori = null,  // Mengosongkan kategori setelah dihapus
                    isLoading = false,
                    isError = false
                )
            } catch (e: Exception) {
                e.printStackTrace()
                // Jika ada error, update UI state untuk menampilkan error
                uiState = uiState.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Gagal menghapus aset"
                )
            }
        }
    }

    fun resetUiState() {
        uiState = DetailKategoriUiState() // Reset state ke default
    }
}

data class DetailKategoriUiState(
    val kategori: Kategori? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

