package com.example.finalprojectpam.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Kategori
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.launch

class KategoriPageViewModel(private val kategoriRepository: KeuanganRepository) : ViewModel() {
    var uiState by mutableStateOf(KategoriPageUiState())
        private set

    init {
        loadKategoriList()
    }

    // Fungsi untuk load daftar kategori dari backend
    private fun loadKategoriList() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true) // Set loading jadi true
            try {
                val kategoriResponse = kategoriRepository.getAllKategori()
                uiState = uiState.copy(
                    isLoading = false,
                    kategoriList = kategoriResponse.data,
                    errorMessage = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Gagal memuat data kategori: ${e.message}"
                )
            }
        }
    }
}

// Data class untuk UI State dengan loading, success, dan error
data class KategoriPageUiState(
    val isLoading: Boolean = false,      // Untuk menampilkan indikator loading
    val kategoriList: List<Kategori> = emptyList(), // Data yang ditampilkan
    val errorMessage: String? = null     // Pesan error jika terjadi kesalahan
)
