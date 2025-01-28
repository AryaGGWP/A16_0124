package com.example.finalprojectpam.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Pengeluaran
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.launch

class PengeluaranPageViewModel(private val pengeluaranRepository: KeuanganRepository) : ViewModel() {
    var uiState by mutableStateOf(PengeluaranPageUiState())
        private set

    init {
        loadPengeluaranList()
    }

    // Fungsi untuk load semua pengeluaran
    private fun loadPengeluaranList() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true) // Tampilkan loading
            try {
                val pengeluaranResponse = pengeluaranRepository.getAllPengeluaran()
                uiState = uiState.copy(
                    isLoading = false,
                    pengeluaranList = pengeluaranResponse.data,
                    errorMessage = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Gagal memuat data pengeluaran: ${e.message}"
                )
            }
        }
    }
}

// Data class untuk UI State
data class PengeluaranPageUiState(
    val isLoading: Boolean = false,              // Indikator loading
    val pengeluaranList: List<Pengeluaran> = emptyList(), // Data pengeluaran
    val errorMessage: String? = null             // Pesan error
)
