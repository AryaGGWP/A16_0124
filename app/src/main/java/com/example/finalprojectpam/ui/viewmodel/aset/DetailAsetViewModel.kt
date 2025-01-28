package com.example.finalprojectpam.ui.viewmodel.aset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Aset
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.launch

class DetailAsetViewModel(private val keuanganRepository: KeuanganRepository) : ViewModel() {
    var uiState by mutableStateOf(DetailAsetUiState())
        private set

    fun getAsetById(idAset: Int) {
        viewModelScope.launch {
            try {
                val aset = keuanganRepository.getAsetById(idAset)
                uiState = DetailAsetUiState(
                    aset = aset,
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

    // Fungsi untuk menghapus aset berdasarkan id
    fun deleteAset(idAset: Int) {
        viewModelScope.launch {
            try {
                // Memanggil repository untuk menghapus aset
                keuanganRepository.deleteAset(idAset)

                // Setelah berhasil menghapus aset, update UI state
                uiState = uiState.copy(
                    aset = null,  // Mengosongkan aset setelah dihapus
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
        uiState = DetailAsetUiState() // Reset state ke default
    }
}

data class DetailAsetUiState(
    val aset: Aset? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

