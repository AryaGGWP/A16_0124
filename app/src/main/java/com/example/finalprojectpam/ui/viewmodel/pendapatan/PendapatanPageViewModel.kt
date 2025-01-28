package com.example.finalprojectpam.ui.viewmodel.pendapatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Pendapatan
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.launch

class PendapatanPageViewModel(private val pendapatanRepository: KeuanganRepository) : ViewModel() {
    var uiState by mutableStateOf(PendapatanPageUiState())
        private set

    init {
        loadPendapatanList()
    }

    // Fungsi untuk load semua pendapatan
    private fun loadPendapatanList() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true) // Tampilkan loading
            try {
                val pendapatanResponse = pendapatanRepository.getAllPendapatan()
                uiState = uiState.copy(
                    isLoading = false,
                    pendapatanList = pendapatanResponse.data,
                    errorMessage = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Gagal memuat data pendapatan: ${e.message}"
                )
            }
        }
    }
}

// Data class untuk UI State
data class PendapatanPageUiState(
    val isLoading: Boolean = false,            // Indikator loading
    val pendapatanList: List<Pendapatan> = emptyList(), // Data pendapatan
    val errorMessage: String? = null           // Pesan error
)
