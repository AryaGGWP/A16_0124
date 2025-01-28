package com.example.finalprojectpam.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Aset
import com.example.finalprojectpam.model.Kategori
import com.example.finalprojectpam.model.Pengeluaran
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.launch

class UpdatePengeluaranViewModel(private val pengeluaranRepository: KeuanganRepository) : ViewModel() {
    // State
    var uiState by mutableStateOf(UpdatePengeluaranUiState())
        private set

    // Init untuk load data aset dan kategori
    init {
        loadAsetList()
        loadKategoriList()
    }

    // Fungsi untuk load list aset
    private fun loadAsetList() {
        viewModelScope.launch {
            try {
                val asetResponse = pengeluaranRepository.getAllAset()
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
                val kategoriResponse = pengeluaranRepository.getAllKategori()
                uiState = uiState.copy(kategoriList = kategoriResponse.data)
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(snackBarMessage = "Gagal memuat data kategori")
            }
        }
    }

    // Fungsi untuk load detail pengeluaran (pre-fill data di form)
    fun loadPengeluaranForUpdate(idPengeluaran: Int) {
        viewModelScope.launch {
            try {
                val pengeluaran = pengeluaranRepository.getPengeluaranById(idPengeluaran)
                uiState = uiState.copy(
                    updatePengeluaranUiEvent = pengeluaran.toUpdatePengeluaranUiEvent()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(snackBarMessage = "Gagal memuat data pengeluaran")
            }
        }
    }

    // Fungsi untuk meng-update pengeluaran
    fun updatePengeluaran() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    pengeluaranRepository.updatePengeluaran(
                        idPengeluaran = uiState.updatePengeluaranUiEvent.idPengeluaran,
                        pengeluaran = uiState.updatePengeluaranUiEvent.toPengeluaran()
                    )

                    // Update UI State setelah berhasil
                    uiState = uiState.copy(
                        snackBarMessage = "Data pengeluaran berhasil diperbarui",
                        isEntryValid = FormErrorStateUpdatePengeluaran() // Reset error state
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    uiState = uiState.copy(snackBarMessage = "Gagal memperbarui data pengeluaran")
                }
            }
        } else {
            uiState = uiState.copy(snackBarMessage = "Input tidak valid. Periksa kembali data Anda.")
        }
    }

    // Fungsi untuk validasi input
    private fun validateFields(): Boolean {
        val event = uiState.updatePengeluaranUiEvent
        val errorState = FormErrorStateUpdatePengeluaran(
            asetError = if (event.idAset == 0) "Aset harus dipilih" else null,
            kategoriError = if (event.idKategori == 0) "Kategori harus dipilih" else null,
            tanggalError = if (event.tanggalTransaksi.isBlank()) "Tanggal transaksi tidak boleh kosong" else null,
            totalError = if (event.total <= 0) "Total pengeluaran harus lebih dari 0" else null
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Fungsi untuk update UI State berdasarkan input pengguna
    fun updateUiState(updatePengeluaranUiEvent: UpdatePengeluaranUiEvent) {
        uiState = uiState.copy(updatePengeluaranUiEvent = updatePengeluaranUiEvent)
    }

    // Fungsi untuk reset snackBarMessage setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

// Data class untuk UI State
data class UpdatePengeluaranUiState(
    val updatePengeluaranUiEvent: UpdatePengeluaranUiEvent = UpdatePengeluaranUiEvent(),
    val asetList: List<Aset> = emptyList(),
    val kategoriList: List<Kategori> = emptyList(),
    val snackBarMessage: String? = null,
    val isEntryValid: FormErrorStateUpdatePengeluaran = FormErrorStateUpdatePengeluaran()
)

// Data class untuk validasi input
data class FormErrorStateUpdatePengeluaran(
    val asetError: String? = null,
    val kategoriError: String? = null,
    val tanggalError: String? = null,
    val totalError: String? = null
) {
    fun isValid(): Boolean {
        return asetError == null && kategoriError == null && tanggalError == null && totalError == null
    }
}

// Data class untuk event update pengeluaran
data class UpdatePengeluaranUiEvent(
    val idPengeluaran: Int = 0,
    val idAset: Int = 0,
    val idKategori: Int = 0,
    val tanggalTransaksi: String = "",
    val total: Double = 0.0,
    val catatan: String = ""
)

// Ekstensi untuk konversi
fun UpdatePengeluaranUiEvent.toPengeluaran(): Pengeluaran = Pengeluaran(
    idPengeluaran = idPengeluaran,
    idAset = idAset,
    idKategori = idKategori,
    tanggalTransaksi = tanggalTransaksi,
    total = total,
    catatan = catatan
)

fun Pengeluaran.toUpdatePengeluaranUiEvent(): UpdatePengeluaranUiEvent = UpdatePengeluaranUiEvent(
    idPengeluaran = idPengeluaran,
    idAset = idAset,
    idKategori = idKategori,
    tanggalTransaksi = tanggalTransaksi,
    total = total,
    catatan = catatan
)

