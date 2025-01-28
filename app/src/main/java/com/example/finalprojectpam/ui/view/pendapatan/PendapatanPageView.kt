package com.example.finalprojectpam.ui.view.pendapatan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalprojectpam.model.Pendapatan
import com.example.finalprojectpam.ui.customwidget.BottomAppBar
import com.example.finalprojectpam.ui.customwidget.CustomTopAppBar
import com.example.finalprojectpam.ui.viewmodel.Home.HomeViewModel
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.pendapatan.PendapatanPageUiState
import com.example.finalprojectpam.ui.viewmodel.pendapatan.PendapatanPageViewModel
import kotlinx.coroutines.launch

@Composable
fun PendapatanPageView(
    viewModel: PendapatanPageViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelHome: HomeViewModel,
    onDetailPendapatan: (Int) -> Unit = {}, // Navigasi ke detail pendapatan dengan ID
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                judul = "Daftar Pendapatan",
                judulKecil = "Kelola pendapatan Anda",
                showBackButton = false,
                showProfile = true,
                showJudulKecil = true,
                showSaldo = false, // Tidak menampilkan saldo di halaman ini
                onBack = {},
                modifier = modifier,
                homeViewModel = viewModelHome // Untuk konsistensi
            )
        },
        bottomBar = {
            BottomAppBar(
                showHomeClick = true,
                showPageAset = true,
                showPageKategori = true,
                showPagePendapatan = false, // Tidak aktifkan tombol pendapatan di halaman ini
                showPagePengeluaran = true
            )
        }
    ) { innerPadding ->
        val uiState = viewModel.uiState
        BodyPendapatanPageView(
            uiState = uiState,
            onDetailPendapatan = onDetailPendapatan,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyPendapatanPageView(
    uiState: PendapatanPageUiState,
    onDetailPendapatan: (Int) -> Unit = {}, // Aksi untuk navigasi ke detail pendapatan
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    when {
        uiState.isLoading -> {
            // Loading Indicator
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage != null -> {
            // Show Snackbar for Error Message
            LaunchedEffect(uiState.errorMessage) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.errorMessage)
                }
            }
        }

        uiState.pendapatanList.isEmpty() -> {
            // Empty State
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data pendapatan.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        else -> {
            // Display List of Pendapatan
            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {
                items(
                    items = uiState.pendapatanList,
                    key = { it.idPendapatan }
                ) { pendapatan ->
                    PendapatanCard(
                        pendapatan = pendapatan,
                        onClick = { onDetailPendapatan(pendapatan.idPendapatan) }
                    )
                }
            }
        }
    }
}

@Composable
fun PendapatanCard(
    pendapatan: Pendapatan,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick), // Navigasi ke detail pendapatan
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon for Pendapatan
            Icon(
                imageVector = Icons.Default.Money,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Pendapatan Information
            Column {
                Text(
                    text = pendapatan.catatan,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Total: Rp. ${pendapatan.total}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tanggal: ${pendapatan.tanggalTransaksi}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Aset: ${pendapatan.namaAset}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Kategori: ${pendapatan.namaKategori}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}


