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

class InsertPengeluaranViewModel(private val pengeluaranRepository: KeuanganRepository) : ViewModel() {
    // State
    var uiState by mutableStateOf(InsertPengeluaranUiState())
        private set

    // Init untuk load data aset dan kategori
    init {
        loadAsetList()
        loadKategoriList()
    }

    // Fungsi untuk load list aset dari backend
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

    // Fungsi untuk load list kategori dari backend
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

    // Fungsi untuk meng-handle pilihan aset dari dropdown
    fun onAsetSelected(index: Int) {
        val selectedAset = uiState.asetList[index]
        uiState = uiState.copy(
            namaAset = selectedAset.namaAset,
            insertPengeluaranUiEvent = uiState.insertPengeluaranUiEvent.copy(idAset = selectedAset.idAset)
        )
    }

    // Fungsi untuk meng-handle pilihan kategori dari dropdown
    fun onKategoriSelected(index: Int) {
        val selectedKategori = uiState.kategoriList[index]
        uiState = uiState.copy(
            namaKategori = selectedKategori.namaKategori,
            insertPengeluaranUiEvent = uiState.insertPengeluaranUiEvent.copy(idKategori = selectedKategori.idKategori)
        )
    }

    // Fungsi untuk update state saat user mengisi form pengeluaran
    fun updateInsertPengeluaranState(insertPengeluaranUiEvent: InsertPengeluaranUiEvent) {
        uiState = uiState.copy(
            insertPengeluaranUiEvent = insertPengeluaranUiEvent
        )
    }

    // Validasi input menggunakan FormErrorState
    private fun validateFields(): Boolean {
        val event = uiState.insertPengeluaranUiEvent
        val errorState = FormErrorStateInsertPengeluaran(
            asetError = if (event.idAset == 0) "Aset harus dipilih" else null,
            kategoriError = if (event.idKategori == 0) "Kategori harus dipilih" else null,
            tanggalError = if (event.tanggalTransaksi.isBlank()) "Tanggal transaksi tidak boleh kosong" else null,
            totalError = if (event.total <= 0) "Total pengeluaran harus lebih dari 0" else null
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Fungsi untuk insert pengeluaran ke backend
    fun insertPengeluaran() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    val pengeluaran = uiState.insertPengeluaranUiEvent.toPengeluaran()
                    pengeluaranRepository.insertPengeluaran(pengeluaran)

                    // Update UI State setelah berhasil
                    uiState = uiState.copy(
                        snackBarMessage = "Data pengeluaran berhasil disimpan",
                        insertPengeluaranUiEvent = InsertPengeluaranUiEvent(),
                        namaAset = "",
                        namaKategori = "",
                        isEntryValid = FormErrorStateInsertPengeluaran() // Reset error state
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    uiState = uiState.copy(snackBarMessage = "Gagal menyimpan data pengeluaran")
                }
            }
        } else {
            uiState = uiState.copy(snackBarMessage = "Input tidak valid. Periksa kembali data Anda.")
        }
    }

    // Fungsi untuk mereset snackBarMessage setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

// Data class untuk UI State
data class InsertPengeluaranUiState(
    val insertPengeluaranUiEvent: InsertPengeluaranUiEvent = InsertPengeluaranUiEvent(),
    val asetList: List<Aset> = emptyList(),
    val kategoriList: List<Kategori> = emptyList(),
    val namaAset: String = "",
    val namaKategori: String = "",
    val snackBarMessage: String? = null,
    val isEntryValid: FormErrorStateInsertPengeluaran = FormErrorStateInsertPengeluaran()
)

// Data class untuk validasi input
data class FormErrorStateInsertPengeluaran(
    val asetError: String? = null,
    val kategoriError: String? = null,
    val tanggalError: String? = null,
    val totalError: String? = null
) {
    fun isValid(): Boolean {
        return asetError == null && kategoriError == null && tanggalError == null && totalError == null
    }
}

// Data class untuk event insert pengeluaran
data class InsertPengeluaranUiEvent(
    val idAset: Int = 0,
    val idKategori: Int = 0,
    val idPengeluaran: Int = 0,
    val tanggalTransaksi: String = "",
    val total: Double = 0.0,
    val catatan: String = ""
)

// Ekstensi untuk konversi
fun InsertPengeluaranUiEvent.toPengeluaran(): Pengeluaran = Pengeluaran(
    idPengeluaran = idPengeluaran,
    idAset = idAset,
    idKategori = idKategori,
    tanggalTransaksi = tanggalTransaksi,
    total = total,
    catatan = catatan
)



