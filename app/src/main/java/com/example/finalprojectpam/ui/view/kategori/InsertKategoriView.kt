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
import com.example.finalprojectpam.ui.navigation.DestinasiNavigasi
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.kategori.InsertKategoriViewModel
import com.example.finalprojectpam.R
import com.example.finalprojectpam.ui.viewmodel.Home.HomeViewModel
import kotlinx.coroutines.launch

object DestinasiInsertKategori : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Insert Kategori"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertKategoriView(
    onBack: () -> Unit,
    onKategoriInserted: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: InsertKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory),
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
                judul = "Insert Kategori",
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
                    text = "Masukkan Nama Kategori",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = Color.Black
                )

                // Input Nama Kategori
                OutlinedTextField(
                    value = uiState.insertKategoriEvent.namaKategori, // Update to namaKategori
                    onValueChange = { newValue ->
                        viewModel.updateInsertKategoriState( // Update method call
                            uiState.insertKategoriEvent.copy(namaKategori = newValue)
                        )
                    },
                    label = { Text("Nama Kategori") }, // Update label
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = uiState.isEntryValid.namaKategori != null // Show error state if there's a validation error
                )

                // Display error message if validation fails
                uiState.isEntryValid.namaKategori?.let { errorMessage ->
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
                        viewModel.insertKategori() // Call insert function which handles validation internally
                    }
                    onNavigate()
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

