package com.example.finalprojectpam.ui.viewmodel.pendapatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Aset
import com.example.finalprojectpam.model.Kategori
import com.example.finalprojectpam.model.Pendapatan
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.launch

class UpdatePendapatanViewModel(private val pendapatanRepository: KeuanganRepository) : ViewModel() {
    // State
    var uiState by mutableStateOf(UpdatePendapatanUiState())
        private set

    // Init untuk load data aset dan kategori
    init {
        loadAsetList()
        loadKategoriList()
        updatePendapatan()
    }

    // Fungsi untuk load list aset
    private fun loadAsetList() {
        viewModelScope.launch {
            try {
                val asetResponse = pendapatanRepository.getAllAset()
                uiState = uiState.copy(asetList = asetResponse.data)
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(snackBarMessage = "Gagal memuat data aset")
            }
        }
    }

    // Fungsi untuk load list kategori
    private fun loadKategoriList() {
        viewModelScope.launch {
            try {
                val kategoriResponse = pendapatanRepository.getAllKategori()
                uiState = uiState.copy(kategoriList = kategoriResponse.data)
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(snackBarMessage = "Gagal memuat data kategori")
            }
        }
    }

    // Fungsi untuk load detail pendapatan (pre-fill data di form)
    fun loadPendapatanForUpdate(idPendapatan: Int) {
        viewModelScope.launch {
            try {
                val pendapatan = pendapatanRepository.getPendapatanById(idPendapatan)
                uiState = uiState.copy(
                    updatePendapatanUiEvent = pendapatan.toUpdatePendapatanUiEvent()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(snackBarMessage = "Gagal memuat data pendapatan")
            }
        }
    }

    // Fungsi untuk update pendapatan
    private fun updatePendapatan() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    pendapatanRepository.updatePendapatan(
                        idPendapatan = uiState.updatePendapatanUiEvent.idPendapatan,
                        pendapatan = uiState.updatePendapatanUiEvent.toPendapatan()
                    )

                    // Update UI State setelah berhasil
                    uiState = uiState.copy(
                        snackBarMessage = "Data pendapatan berhasil diperbarui",
                        isEntryValid = FormErrorStateUpdatePendapatan() // Reset error state
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    uiState = uiState.copy(snackBarMessage = "Gagal memperbarui data pendapatan")
                }
            }
        } else {
            uiState = uiState.copy(snackBarMessage = "Input tidak valid. Periksa kembali data Anda.")
        }
    }

    // Fungsi untuk validasi input
    private fun validateFields(): Boolean {
        val event = uiState.updatePendapatanUiEvent
        val errorState = FormErrorStateUpdatePendapatan(
            asetError = if (event.idAset == 0) "Aset harus dipilih" else null,
            kategoriError = if (event.idKategori == 0) "Kategori harus dipilih" else null,
            tanggalError = if (event.tanggalTransaksi.isBlank()) "Tanggal transaksi tidak boleh kosong" else null,
            totalError = if (event.total <= 0) "Total pendapatan harus lebih dari 0" else null
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Fungsi untuk update UI State berdasarkan input pengguna
    fun updateUiState(updatePendapatanUiEvent: UpdatePendapatanUiEvent) {
        uiState = uiState.copy(updatePendapatanUiEvent = updatePendapatanUiEvent)
    }

    // Fungsi untuk reset snackBarMessage setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

// Data class untuk UI State
data class UpdatePendapatanUiState(
    val updatePendapatanUiEvent: UpdatePendapatanUiEvent = UpdatePendapatanUiEvent(),
    val asetList: List<Aset> = emptyList(),
    val kategoriList: List<Kategori> = emptyList(),
    val snackBarMessage: String? = null,
    val isEntryValid: FormErrorStateUpdatePendapatan = FormErrorStateUpdatePendapatan()
)

// Data class untuk validasi input
data class FormErrorStateUpdatePendapatan(
    val asetError: String? = null,
    val kategoriError: String? = null,
    val tanggalError: String? = null,
    val totalError: String? = null
) {
    fun isValid(): Boolean {
        return asetError == null && kategoriError == null && tanggalError == null && totalError == null
    }
}

// Data class untuk event update pendapatan
data class UpdatePendapatanUiEvent(
    val idPendapatan: Int = 0,
    val idAset: Int = 0,
    val idKategori: Int = 0,
    val tanggalTransaksi: String = "",
    val total: Double = 0.0,
    val catatan: String = ""
)

// Ekstensi untuk konversi
fun UpdatePendapatanUiEvent.toPendapatan(): Pendapatan = Pendapatan(
    idPendapatan = idPendapatan,
    idAset = idAset,
    idKategori = idKategori,
    tanggalTransaksi = tanggalTransaksi,
    total = total,
    catatan = catatan
)

fun Pendapatan.toUpdatePendapatanUiEvent(): UpdatePendapatanUiEvent = UpdatePendapatanUiEvent(
    idPendapatan = idPendapatan,
    idAset = idAset,
    idKategori = idKategori,
    tanggalTransaksi = tanggalTransaksi,
    total = total,
    catatan = catatan
)

