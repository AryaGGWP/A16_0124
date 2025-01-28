package com.example.finalprojectpam.ui.view.pendapatan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Label
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.finalprojectpam.ui.customwidget.BottomAppBar
import com.example.finalprojectpam.ui.customwidget.CustomTopAppBar
import com.example.finalprojectpam.ui.navigation.DestinasiUpdatePendapatan
import com.example.finalprojectpam.ui.viewmodel.Home.HomeViewModel
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.pendapatan.DetailPendapatanUiState
import com.example.finalprojectpam.ui.viewmodel.pendapatan.DetailPendapatanViewModel

@Composable
fun DetailPendapatanView(
    viewModel: DetailPendapatanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navController: NavHostController,
    viewModelHome: HomeViewModel,
    onBack: () -> Unit = {},
    onEditClick: (Int) -> Unit = {}, // Navigasi ke Edit Pendapatan dengan ID
    onDeleteClick: () -> Unit = {}, // Aksi setelah delete berhasil
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                judul = "Kembali",
                judulKecil = "",
                showBackButton = true,
                showProfile = false,
                showJudulKecil = false,
                showSaldo = false,
                onBack = onBack,
                modifier = modifier,
                homeViewModel = viewModelHome
            )
        },
        bottomBar = {
            BottomAppBar(
                showHomeClick = false,
                showPageAset = false,
                showPageKategori = false,
                showPagePendapatan = false,
                showPagePengeluaran = false
            )
        }
    ) { innerPadding ->
        val uiState = viewModel.uiState
        BodyDetailPendapatanView(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            onEditClick = { idPendapatan ->
                // Navigasi ke halaman update pendapatan
                navController.navigate("${DestinasiUpdatePendapatan.route}/$idPendapatan")
            },
            onDeleteClick = {
                viewModel.deletePendapatan(uiState.idPendapatan)
                onDeleteClick()
            }
        )
    }
}

@Composable
fun BodyDetailPendapatanView(
    uiState: DetailPendapatanUiState,
    onEditClick: (Int) -> Unit = {},
    onDeleteClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    when {
        uiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        uiState.isError -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.errorMessage ?: "Gagal memuat data pendapatan.")
            }
        }
        else -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Detail Pendapatan Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Detail Pendapatan",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        DetailPendapatanItem(icon = Icons.Default.Info, label = "ID Pendapatan", value = uiState.idPendapatan.toString())
                        DetailPendapatanItem(icon = Icons.Default.Folder, label = "Aset", value = uiState.namaAset)
                        DetailPendapatanItem(icon = Icons.Default.Label, label = "Kategori", value = uiState.namaKategori)
                        DetailPendapatanItem(icon = Icons.Default.DateRange, label = "Tanggal Transaksi", value = uiState.tanggalTransaksi)
                        DetailPendapatanItem(icon = Icons.Default.AttachMoney, label = "Total", value = "Rp. ${uiState.total}")
                        DetailPendapatanItem(icon = Icons.Default.Description, label = "Catatan", value = uiState.catatan)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons Row (Edit & Delete)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = { onEditClick(uiState.idPendapatan) },
                        modifier = Modifier
                            .size(50.dp)
                            .background(MaterialTheme.colorScheme.secondary, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier
                            .size(50.dp)
                            .background(MaterialTheme.colorScheme.error, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                    }
                }

                // Delete Confirmation Dialog
                if (showDeleteDialog) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            showDeleteDialog = false
                            onDeleteClick()
                        },
                        onDeleteCancel = {
                            showDeleteDialog = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DetailPendapatanItem(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { onDeleteCancel() },
        title = {
            Text(
                text = "Hapus Pendapatan",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(text = "Apakah Anda yakin ingin menghapus pendapatan ini?")
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Ya", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Batal")
            }
        }
    )
}
