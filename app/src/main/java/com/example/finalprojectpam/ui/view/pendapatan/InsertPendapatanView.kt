package com.example.finalprojectpam.ui.view.pendapatan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalprojectpam.ui.customwidget.BottomAppBar
import com.example.finalprojectpam.ui.customwidget.CustomTopAppBar
import com.example.finalprojectpam.ui.customwidget.DynamicSelectedField
import com.example.finalprojectpam.ui.viewmodel.Home.HomeViewModel
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.pendapatan.InsertPendapatanViewModel
import com.example.finalprojectpam.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPendapatanView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: InsertPendapatanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelHome: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Menampilkan Snackbar jika ada pesan
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage() // Reset snackbar message after showing it
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                judul = "Insert Pendapatan",
                judulKecil = "",
                showBackButton = true,
                showProfile = false,
                showJudulKecil = false,
                showSaldo = false,
                onBack = onBack,
                modifier = Modifier,
                homeViewModel = viewModelHome
            )
        },
        bottomBar = {
            BottomAppBar(
                showHomeClick = true,
                showPageAset = false,
                showPageKategori = false,
                showPagePendapatan = true,
                showPagePengeluaran = false
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Masukkan Detail Pendapatan",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Dropdown Nama Aset
                    DynamicSelectedField(
                        selectedValue = uiState.namaAset,
                        options = uiState.asetList.map { it.namaAset },
                        label = "Pilih Aset",
                        placeholder = "Pilih aset",
                        onValueChangedEvent = { selectedName ->
                            val index = uiState.asetList.indexOfFirst { it.namaAset == selectedName }
                            if (index != -1) {
                                viewModel.onAsetSelected(index)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    uiState.isEntryValid.asetError?.let { errorMessage ->
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Dropdown Nama Kategori
                    DynamicSelectedField(
                        selectedValue = uiState.namaKategori,
                        options = uiState.kategoriList.map { it.namaKategori },
                        label = "Pilih Kategori",
                        placeholder = "Pilih kategori",
                        onValueChangedEvent = { selectedName ->
                            val index = uiState.kategoriList.indexOfFirst { it.namaKategori == selectedName }
                            if (index != -1) {
                                viewModel.onKategoriSelected(index)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    uiState.isEntryValid.kategoriError?.let { errorMessage ->
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Input Tanggal Transaksi
                    OutlinedTextField(
                        value = uiState.insertPendapatanUiEvent.tanggalTransaksi,
                        onValueChange = { newValue ->
                            viewModel.updateInsertPendapatanState(
                                uiState.insertPendapatanUiEvent.copy(tanggalTransaksi = newValue)
                            )
                        },
                        label = { Text("Tanggal Transaksi") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = uiState.isEntryValid.tanggalError != null
                    )
                    uiState.isEntryValid.tanggalError?.let { errorMessage ->
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Input Total Pendapatan
                    OutlinedTextField(
                        value = if (uiState.insertPendapatanUiEvent.total == 0.0) "" else uiState.insertPendapatanUiEvent.total.toString(),
                        onValueChange = { newValue ->
                            val total = newValue.toDoubleOrNull() ?: 0.0
                            viewModel.updateInsertPendapatanState(
                                uiState.insertPendapatanUiEvent.copy(total = total)
                            )
                        },
                        label = { Text("Total Pendapatan") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = uiState.isEntryValid.totalError != null
                    )
                    uiState.isEntryValid.totalError?.let { errorMessage ->
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Input Catatan
                    OutlinedTextField(
                        value = uiState.insertPendapatanUiEvent.catatan,
                        onValueChange = { newValue ->
                            viewModel.updateInsertPendapatanState(
                                uiState.insertPendapatanUiEvent.copy(catatan = newValue)
                            )
                        },
                        label = { Text("Catatan") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false,
                        isError = false
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Simpan
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.insertPendapatan()
                    }
                    onNavigate
                },
                modifier = Modifier
                    .size(56.dp)
                    .background(colorResource(id = R.color.purple_200), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Simpan",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}