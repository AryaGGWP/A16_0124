package com.example.finalprojectpam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalprojectpam.KeuanganApplications
import com.example.finalprojectpam.ui.viewmodel.Home.HomeViewModel
import com.example.finalprojectpam.ui.viewmodel.aset.AsetPageViewModel
import com.example.finalprojectpam.ui.viewmodel.aset.DetailAsetViewModel
import com.example.finalprojectpam.ui.viewmodel.aset.InsertAsetViewModel
import com.example.finalprojectpam.ui.viewmodel.aset.UpdateAsetViewModel
import com.example.finalprojectpam.ui.viewmodel.kategori.DetailKategoriViewModel
import com.example.finalprojectpam.ui.viewmodel.kategori.InsertKategoriViewModel
import com.example.finalprojectpam.ui.viewmodel.kategori.KategoriPageViewModel
import com.example.finalprojectpam.ui.viewmodel.kategori.UpdateKategoriViewModel
import com.example.finalprojectpam.ui.viewmodel.pendapatan.DetailPendapatanViewModel
import com.example.finalprojectpam.ui.viewmodel.pendapatan.InsertPendapatanViewModel
import com.example.finalprojectpam.ui.viewmodel.pendapatan.PendapatanPageViewModel
import com.example.finalprojectpam.ui.viewmodel.pendapatan.UpdatePendapatanViewModel
import com.example.finalprojectpam.ui.viewmodel.pengeluaran.DetailPengeluaranViewModel
import com.example.finalprojectpam.ui.viewmodel.pengeluaran.InsertPengeluaranViewModel
import com.example.finalprojectpam.ui.viewmodel.pengeluaran.PengeluaranPageViewModel
import com.example.finalprojectpam.ui.viewmodel.pengeluaran.UpdatePengeluaranViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { AsetPageViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { InsertAsetViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { DetailAsetViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { UpdateAsetViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { KategoriPageViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { InsertKategoriViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { UpdateKategoriViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { DetailKategoriViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { PendapatanPageViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { DetailPendapatanViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { InsertPendapatanViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { UpdatePendapatanViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { PengeluaranPageViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { DetailPengeluaranViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { InsertPengeluaranViewModel(aplikasiKeuangan().container.kontakRepository) }
        initializer { UpdatePengeluaranViewModel(aplikasiKeuangan().container.kontakRepository) }
    }
}

// Ekstensi untuk mendapatkan instance aplikasi
fun CreationExtras.aplikasiKeuangan(): KeuanganApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KeuanganApplications)
