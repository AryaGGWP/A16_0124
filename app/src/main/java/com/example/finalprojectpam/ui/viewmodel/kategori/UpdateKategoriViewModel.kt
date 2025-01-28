package com.example.finalprojectpam.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Aset
import com.example.finalprojectpam.model.Kategori
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.launch

class UpdateKategoriViewModel(private val repository: KeuanganRepository) : ViewModel() {
    // State untuk UI
    var uiState by mutableStateOf(UpdateKategoriUiState())
        private set

    // Fungsi untuk mengisi data awal kategori berdasarkan ID
    fun getKategoriById(idKategori: Int) {
        viewModelScope.launch {
            try {
                val kategori = repository.getKategoriById(idKategori)
                uiState = kategori.toUiStateKategori()
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(snackBarMessage = "Gagal memuat data kategori")
            }
        }
    }

    // Fungsi untuk meng-update UI State berdasarkan input pengguna
    fun updateKategoriUiEvent(updateKategoriUiEvent: UpdateKategoriUiEvent) {
        uiState = uiState.copy(
            updateKategoriUiEvent = updateKategoriUiEvent
        )
    }

    // Validasi input menggunakan FormErrorState
    private fun validateFields(): Boolean {
        val event = uiState.updateKategoriUiEvent
        val errorStateUpdate = FormErrorStateUpdate(
            namaKategoriError = if (event.namaKategori.isBlank()) "Nama kategori tidak boleh kosong" else null
        )

        uiState = uiState.copy(isEntryValid = errorStateUpdate)
        return errorStateUpdate.isValid()
    }

    // Fungsi untuk mengirim data kategori yang diperbarui ke backend
    fun updateKategori(idKategori: Int) {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repository.updateKategori(idKategori, uiState.updateKategoriUiEvent.toKategori())

                    // Update UI State setelah berhasil
                    uiState = uiState.copy(
                        snackBarMessage = "Data kategori berhasil diperbarui",
                        isEntryValid = FormErrorStateUpdate() // Reset error state
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    uiState = uiState.copy(snackBarMessage = "Gagal memperbarui data kategori")
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
data class UpdateKategoriUiState(
    val updateKategoriUiEvent: UpdateKategoriUiEvent = UpdateKategoriUiEvent(),
    val snackBarMessage: String? = null,         // Feedback snackbar
    val isEntryValid: FormErrorStateUpdate = FormErrorStateUpdate() // State validasi
)

// Data untuk UI Event
data class UpdateKategoriUiEvent(
    val namaKategori: String = ""
)

data class FormErrorStateUpdate(
    val namaKategoriError: String? = null
) {
    fun isValid(): Boolean {
        return namaKategoriError == null
    }
}

// Konversi dari UiEvent ke Model
fun UpdateKategoriUiEvent.toKategori(): Kategori = Kategori(
    idKategori = 0, // ID kategori diambil dari parameter fungsi
    namaKategori = namaKategori
)

// Konversi dari Model ke UiState
fun Kategori.toUiStateKategori(): UpdateKategoriUiState = UpdateKategoriUiState(
    updateKategoriUiEvent = toUpdateKategoriUiEvent()
)

// Konversi dari Model ke UiEvent
fun Kategori.toUpdateKategoriUiEvent(): UpdateKategoriUiEvent = UpdateKategoriUiEvent(
    namaKategori = namaKategori
)

