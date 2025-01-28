package com.example.finalprojectpam.ui.viewmodel.aset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Aset
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.launch

class InsertAsetViewModel(private val keuanganRepository: KeuanganRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertAsetUiState())
        private set

    fun updateInsertAsetState(event: InsertAsetEvent) {
        uiState = uiState.copy(
            insertAsetEvent = event
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.insertAsetEvent
        val errorState = FormErrorState(
            namaAset = if (event.namaAset.isNotBlank()) null else "Nama aset tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertAset() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    val aset = uiState.insertAsetEvent.toAset()
                    keuanganRepository.insertAset(aset)

                    // Update UI State setelah berhasil
                    uiState = uiState.copy(
                        snackBarMessage = "Data aset berhasil disimpan",
                        insertAsetEvent = InsertAsetEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data aset gagal disimpan, coba lagi nanti"
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
    val namaAset: String? = null
) {
    fun isValid(): Boolean {
        return namaAset == null // Return true jika tidak ada error
    }
}

data class InsertAsetUiState(
    val isEntryValid: FormErrorState = FormErrorState(),
    val insertAsetEvent: InsertAsetEvent = InsertAsetEvent(),
    val snackBarMessage: String? = null
)

data class InsertAsetEvent(
    val namaAset: String = ""
)

// Konversi InsertAsetEvent ke model Aset
fun InsertAsetEvent.toAset(): Aset = Aset(
    idAset = 0, // Default ID, backend yang handle auto-increment
    namaAset = namaAset
)

