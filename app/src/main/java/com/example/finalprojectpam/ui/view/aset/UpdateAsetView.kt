package com.example.finalprojectpam.ui.view.aset

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
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalprojectpam.R
import com.example.finalprojectpam.ui.customwidget.CustomTopAppBar
import com.example.finalprojectpam.ui.viewmodel.Home.HomeViewModel
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.aset.UpdateAsetViewModel
import kotlinx.coroutines.launch

@Composable
fun UpdateAsetView(
    viewModel: UpdateAsetViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelHome: HomeViewModel,
    idAset: Int, // ID aset untuk dimuat
    onBack: () -> Unit = {}, // Navigasi kembali
    onAsetUpdated: () -> Unit = {}, // Aksi setelah aset berhasil diperbarui
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Memuat data aset berdasarkan ID saat halaman dibuka
    LaunchedEffect(idAset) {
        viewModel.getAsetById(idAset)
    }

    // Menampilkan snackbar jika ada pesan
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage() // Reset setelah snackbar ditampilkan
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                judul = "Update Aset",
                judulKecil = "Perbarui detail aset Anda",
                showBackButton = true,
                showProfile = false,
                showJudulKecil = true,
                showSaldo = false,
                onBack = onBack,
                modifier = modifier,
                homeViewModel = viewModelHome // Tidak butuh HomeViewModel di sini
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
            // Form Update Nama Aset
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Update Nama Aset",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Input Nama Aset
                    OutlinedTextField(
                        value = uiState.updateAsetUiEvent.namaAset,
                        onValueChange = { newValue ->
                            viewModel.updateAsetUiEvent(
                                uiState.updateAsetUiEvent.copy(namaAset = newValue)
                            )
                        },
                        label = { Text("Nama Aset") },
                        isError = uiState.isEntryValid.namaAsetError != null,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Pesan error jika input tidak valid
                    uiState.isEntryValid.namaAsetError?.let { errorMessage ->
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Simpan
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateAset(idAset)
                        if (uiState.snackBarMessage == "Data aset berhasil diperbarui") {
                            onAsetUpdated() // Navigasi atau aksi setelah update
                        }
                    }
                },
                modifier = Modifier
                    .size(56.dp)
                    .background(colorResource(id = R.color.purple_200), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = "Simpan",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
