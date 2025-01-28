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
import com.example.finalprojectpam.ui.viewmodel.Home.HomeViewModel
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.aset.InsertAsetViewModel
import com.example.finalprojectpam.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertAsetView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: InsertAsetViewModel = viewModel(factory = PenyediaViewModel.Factory),
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
                judul = "Insert Aset",
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
                showPagePendapatan = false,
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier,
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Masukkan Nama Aset",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = Color.Black
                )

                // Input Nama Aset
                OutlinedTextField(
                    value = uiState.insertAsetEvent.namaAset,
                    onValueChange = { newValue ->
                        viewModel.updateInsertAsetState(
                            uiState.insertAsetEvent.copy(namaAset = newValue)
                        )
                    },
                    label = { Text("Nama Aset") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = uiState.isEntryValid.namaAset != null // Show error state if there's a validation error
                )

                // Display error message if validation fails
                uiState.isEntryValid.namaAset?.let { errorMessage ->
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error, // Use error color from theme
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Simpan
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.insertAset()
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

