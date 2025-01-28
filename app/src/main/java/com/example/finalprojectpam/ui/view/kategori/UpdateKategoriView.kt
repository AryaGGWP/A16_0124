package com.example.finalprojectpam.ui.view.kategori

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.finalprojectpam.R
import com.example.finalprojectpam.ui.customwidget.BottomAppBar
import com.example.finalprojectpam.ui.customwidget.CustomTopAppBar
import com.example.finalprojectpam.ui.viewmodel.Home.HomeViewModel
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.kategori.UpdateKategoriViewModel
import kotlinx.coroutines.launch

@Composable
fun UpdateKategoriView(
    viewModel: UpdateKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelHome: HomeViewModel,
    navController: NavHostController,
    idKategori: Int,
    onBack: () -> Unit = {}
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Inisialisasi data kategori berdasarkan ID
    LaunchedEffect(idKategori) {
        viewModel.getKategoriById(idKategori)
    }

    // Menampilkan Snackbar jika ada pesan
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage() // Reset snackbar message setelah ditampilkan
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                judul = "Edit Kategori",
                judulKecil = "",
                showBackButton = true,
                showProfile = false,
                showJudulKecil = false,
                showSaldo = false,
                onBack = onBack,
                modifier = Modifier,
                homeViewModel = viewModelHome // Untuk konsistensi
            )
        },
        bottomBar = {
            BottomAppBar(
                showHomeClick = true,
                showPageAset = true,
                showPageKategori = false, // Tidak aktifkan tombol kategori di halaman ini
                showPagePendapatan = true,
                showPagePengeluaran = true
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
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
                        text = "Edit Detail Kategori",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Input Nama Kategori
                    OutlinedTextField(
                        value = uiState.updateKategoriUiEvent.namaKategori,
                        onValueChange = { newValue ->
                            viewModel.updateKategoriUiEvent(
                                uiState.updateKategoriUiEvent.copy(namaKategori = newValue)
                            )
                        },
                        label = { Text("Nama Kategori") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = uiState.isEntryValid.namaKategoriError != null
                    )

                    // Error Message for Nama Kategori
                    uiState.isEntryValid.namaKategoriError?.let { errorMessage ->
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Simpan
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateKategori(idKategori)
                        navController.popBackStack() // Kembali ke halaman sebelumnya
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


