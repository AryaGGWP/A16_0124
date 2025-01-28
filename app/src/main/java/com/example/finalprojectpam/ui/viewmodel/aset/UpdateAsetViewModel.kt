package com.example.finalprojectpam.ui.viewmodel.aset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Aset
import com.example.finalprojectpam.model.Kategori
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.launch

class UpdateAsetViewModel(private val repository: KeuanganRepository) : ViewModel() {
    // State untuk UI
    var uiState by mutableStateOf(UpdateAsetUiState())
        private set

    // Fungsi untuk mengisi data awal aset berdasarkan ID
    fun getAsetById(idAset: Int) {
        viewModelScope.launch {
            try {
                val aset = repository.getAsetById(idAset)
                uiState = aset.toUiStateAset()
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(snackBarMessage = "Gagal memuat data aset")
            }
        }
    }

    // Fungsi untuk meng-update UI State berdasarkan input pengguna
    fun updateAsetUiEvent(updateAsetUiEvent: UpdateAsetUiEvent) {
        uiState = uiState.copy(
            updateAsetUiEvent = updateAsetUiEvent
        )
    }

    // Validasi input menggunakan FormErrorState
    private fun validateFields(): Boolean {
        val event = uiState.updateAsetUiEvent
        val errorStateUpdate = FormErrorStateUpdate(
            namaAsetError = if (event.namaAset.isBlank()) "Nama aset tidak boleh kosong" else null
        )

        uiState = uiState.copy(isEntryValid = errorStateUpdate)
        return errorStateUpdate.isValid()
    }

    // Fungsi untuk mengirim data aset yang diperbarui ke backend
    fun updateAset(idAset: Int) {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repository.updateAset(idAset, uiState.updateAsetUiEvent.toAset())

                    // Update UI State setelah berhasil
                    uiState = uiState.copy(
                        snackBarMessage = "Data aset berhasil diperbarui",
                        isEntryValid = FormErrorStateUpdate() // Reset error state
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    uiState = uiState.copy(snackBarMessage = "Gagal memperbarui data aset")
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data Anda."
            )
        }
    }

    // Fungsi untuk mereset snackBarMessage setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

// Data untuk UI State
data class UpdateAsetUiState(
    val updateAsetUiEvent: UpdateAsetUiEvent = UpdateAsetUiEvent(),
    val snackBarMessage: String? = null,         // Feedback snackbar
    val isEntryValid: FormErrorStateUpdate = FormErrorStateUpdate() // State validasi
)

// Data untuk UI Event
data class UpdateAsetUiEvent(
    val namaAset: String = ""
)

data class FormErrorStateUpdate(
    val namaAsetError: String? = null
) {
    fun isValid(): Boolean {
        return namaAsetError == null
    }
}

// Konversi dari UiEvent ke Model
fun UpdateAsetUiEvent.toAset(): Aset = Aset(
    idAset = 0, // ID kategori diambil dari parameter fungsi
    namaAset = namaAset
)

// Konversi dari Model ke UiState
fun Aset.toUiStateAset(): UpdateAsetUiState = UpdateAsetUiState(
    updateAsetUiEvent = toUpdateAsetUiEvent()
)

// Konversi dari Model ke UiEvent
fun Aset.toUpdateAsetUiEvent(): UpdateAsetUiEvent = UpdateAsetUiEvent(
    namaAset = namaAset
)
