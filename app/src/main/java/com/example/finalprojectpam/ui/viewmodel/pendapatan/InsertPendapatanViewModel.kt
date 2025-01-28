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

class InsertPendapatanViewModel(private val pendapatanRepository: KeuanganRepository) : ViewModel() {
    // State
    var uiState by mutableStateOf(InsertPendapatanUiState())
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
                val asetResponse = pendapatanRepository.getAllAset()
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
                val kategoriResponse = pendapatanRepository.getAllKategori()
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
            insertPendapatanUiEvent = uiState.insertPendapatanUiEvent.copy(idAset = selectedAset.idAset)
        )
    }

    // Fungsi untuk meng-handle pilihan kategori dari dropdown
    fun onKategoriSelected(index: Int) {
        val selectedKategori = uiState.kategoriList[index]
        uiState = uiState.copy(
            namaKategori = selectedKategori.namaKategori,
            insertPendapatanUiEvent = uiState.insertPendapatanUiEvent.copy(idKategori = selectedKategori.idKategori)
        )
    }

    // Fungsi untuk update state saat user mengisi form pendapatan
    fun updateInsertPendapatanState(insertPendapatanUiEvent: InsertPendapatanUiEvent) {
        uiState = uiState.copy(
            insertPendapatanUiEvent = insertPendapatanUiEvent
        )
    }

    // Validasi input menggunakan FormErrorState
    private fun validateFields(): Boolean {
        val event = uiState.insertPendapatanUiEvent
        val errorState = FormErrorState(
            asetError = if (event.idAset == 0) "Aset harus dipilih" else null,
            kategoriError = if (event.idKategori == 0) "Kategori harus dipilih" else null,
            tanggalError = if (event.tanggalTransaksi.isBlank()) "Tanggal transaksi tidak boleh kosong" else null,
            totalError = if (event.total <= 0) "Total pendapatan harus lebih dari 0" else null
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Fungsi untuk insert pendapatan ke backend
    fun insertPendapatan() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    val pendapatan = uiState.insertPendapatanUiEvent.toPendapatan()
                    pendapatanRepository.insertPendapatan(pendapatan)

                    // Update UI State setelah berhasil
                    uiState = uiState.copy(
                        snackBarMessage = "Data pendapatan berhasil disimpan",
                        insertPendapatanUiEvent = InsertPendapatanUiEvent(),
                        namaAset = "",
                        namaKategori = "",
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    uiState = uiState.copy(snackBarMessage = "Gagal menyimpan data pendapatan")
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

data class InsertPendapatanUiState(
    val insertPendapatanUiEvent: InsertPendapatanUiEvent = InsertPendapatanUiEvent(),
    val asetList: List<Aset> = emptyList(),         // List aset dari backend
    val kategoriList: List<Kategori> = emptyList(), // List kategori dari backend
    val namaAset: String = "",                     // Nama aset yang dipilih
    val namaKategori: String = "",                 // Nama kategori yang dipilih
    val snackBarMessage: String? = null,           // Pesan snackbar untuk feedback
    val isEntryValid: FormErrorState = FormErrorState()
)

data class FormErrorState(
    val asetError: String? = null,
    val kategoriError: String? = null,
    val tanggalError: String? = null,
    val totalError: String? = null
) {
    fun isValid(): Boolean {
        return asetError == null && kategoriError == null && tanggalError == null && totalError == null
    }
}

data class InsertPendapatanUiEvent(
    val idAset: Int = 0,           // ID aset untuk dikirim ke backend
    val idKategori: Int = 0,       // ID kategori untuk dikirim ke backend
    val idPendapatan: Int = 0,     // ID pendapatan (digunakan jika update)
    val tanggalTransaksi: String = "",
    val total: Double = 0.0,
    val catatan: String = ""
)

// Konversi InsertPendapatanUiEvent ke Pendapatan (untuk backend)
fun InsertPendapatanUiEvent.toPendapatan(): Pendapatan = Pendapatan(
    idPendapatan = idPendapatan,
    idAset = idAset,
    idKategori = idKategori,
    tanggalTransaksi = tanggalTransaksi,
    total = total,
    catatan = catatan
)

