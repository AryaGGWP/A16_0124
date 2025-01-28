package com.example.finalprojectpam.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Kategori
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.launch

class InsertKategoriViewModel(private val keuanganRepository: KeuanganRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertKategoriUiState())
        private set

    fun updateInsertKategoriState(event: InsertKategoriEvent) {
        uiState = uiState.copy(
            insertKategoriEvent = event
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.insertKategoriEvent
        val errorState = FormErrorState(
            namaKategori = if (event.namaKategori.isNotBlank()) null else "Nama kategori tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertKategori() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    val kategori = uiState.insertKategoriEvent.toKategori()
                    keuanganRepository.insertKategori(kategori)

                    // Update UI State setelah berhasil
                    uiState = uiState.copy(
                        snackBarMessage = "Data kategori berhasil disimpan",
                        insertKategoriEvent = InsertKategoriEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data kategori gagal disimpan, coba lagi nanti"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data Anda."
            )
        }
    }

    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class FormErrorState(
    val namaKategori: String? = null
) {
    fun isValid(): Boolean {
        return namaKategori == null // Return true jika tidak ada error
    }
}

data class InsertKategoriUiState(
    val isEntryValid: FormErrorState = FormErrorState(),
    val insertKategoriEvent: InsertKategoriEvent = InsertKategoriEvent(),
    val snackBarMessage: String? = null
)

data class InsertKategoriEvent(
    val namaKategori: String = ""
)

// Konversi InsertKategoriEvent ke model Kategori
fun InsertKategoriEvent.toKategori(): Kategori = Kategori(
    idKategori = 0, // Default ID, backend yang handle auto-increment
    namaKategori = namaKategori
)

