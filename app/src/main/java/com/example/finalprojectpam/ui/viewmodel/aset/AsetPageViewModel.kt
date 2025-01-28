package com.example.finalprojectpam.ui.viewmodel.aset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Aset
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.launch

class AsetPageViewModel(private val asetRepository: KeuanganRepository) : ViewModel() {
    var uiState by mutableStateOf(AsetPageUiState())
        private set

    init {
        loadAsetList()
    }

    // Fungsi untuk load daftar aset dari backend
    private fun loadAsetList() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true) // Set loading jadi true
            try {
                val asetResponse = asetRepository.getAllAset()
                uiState = uiState.copy(
                    isLoading = false,
                    asetList = asetResponse.data,
                    errorMessage = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Gagal memuat data aset: ${e.message}"
                )
            }

        }
    }
}

// Data class untuk UI State dengan loading, success, dan error
data class AsetPageUiState(
    val isLoading: Boolean = false,      // Untuk menampilkan indikator loading
    val asetList: List<Aset> = emptyList(), // Data yang ditampilkan
    val errorMessage: String? = null     // Pesan error jika terjadi kesalahan
)
