package com.example.finalprojectpam.ui.viewmodel.Home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.repository.KeuanganRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(
        val saldo: Double,
        val totalPendapatan: Double,
        val totalPengeluaran: Double
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel(private val keuangan: KeuanganRepository) : ViewModel() {
    private val _saldo = MutableStateFlow(0.0)
    val saldo: StateFlow<Double> = _saldo

    private val _totalPengeluaran = MutableStateFlow(0.0)
    val totalPengeluaran: StateFlow<Double> = _totalPengeluaran

    private val _totalPendapatan = MutableStateFlow(0.0)
    val totalPendapatan: StateFlow<Double> = _totalPendapatan
    private var keuanganUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        fetchManajemenKeuangan()
    }

    private fun fetchManajemenKeuangan() {
        viewModelScope.launch {
            keuanganUIState = HomeUiState.Loading
            keuanganUIState = try {
                val response = keuangan.getSaldo()
                if (response.status) {
                    HomeUiState.Success(
                        saldo = response.saldo,
                        totalPendapatan = response.totalPendapatan,
                        totalPengeluaran = response.totalPengeluaran
                    )
                } else {
                    HomeUiState.Error("Failed to fetch data")
                }
            } catch (e: Exception) {
                HomeUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
