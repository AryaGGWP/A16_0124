package com.example.finalprojectpam.ui.view.aset

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
import androidx.compose.material.icons.filled.Folder
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
import androidx.navigation.NavHostController
import com.example.finalprojectpam.model.Aset
import com.example.finalprojectpam.ui.customwidget.BottomAppBar
import com.example.finalprojectpam.ui.customwidget.CustomTopAppBar
import com.example.finalprojectpam.ui.navigation.DestinasiDetailAset
import com.example.finalprojectpam.ui.viewmodel.Home.HomeViewModel
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.aset.AsetPageUiState
import com.example.finalprojectpam.ui.viewmodel.aset.AsetPageViewModel
import kotlinx.coroutines.launch

@Composable
fun AsetPageView(
    viewModel: AsetPageViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelHome: HomeViewModel,
    navController: NavHostController,
    onDetailAset: (Int) -> Unit = {}, // Navigasi ke detail aset dengan ID
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                judul = "Daftar Aset",
                judulKecil = "Kelola aset Anda",
                showBackButton = false,
                showProfile = true,
                showJudulKecil = true,
                showSaldo = false,
                onBack = {},
                modifier = modifier,
                homeViewModel = viewModelHome// Tidak butuh HomeViewModel di sini
            )
        },
        bottomBar = {
            BottomAppBar(
                showHomeClick = true,
                showPageAset = false,
                showPageKategori = true,
                showPagePendapatan = true,
                showPagePengeluaran = true
            )
        }
    ) { innerPadding ->
        val uiState = viewModel.uiState
        BodyAsetPageView(
            uiState = uiState,
            onDetailAset = {idAset ->
                navController.navigate("${DestinasiDetailAset.route}/$idAset")},
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyAsetPageView(
    uiState: AsetPageUiState,
    onDetailAset: (Int) -> Unit = {},
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

        uiState.asetList.isEmpty() -> {
            // Empty State
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data aset.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        else -> {
            // Display List of Aset
            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {
                items(
                    items = uiState.asetList,
                    key = { it.idAset }
                ) { aset ->
                    AsetCard(aset = aset, onClick = { onDetailAset(aset.idAset) })
                }
            }
        }
    }
}

@Composable
fun AsetCard(
    aset: Aset,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick), // Navigasi ke detail aset
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
            // Icon for Aset
            Icon(
                imageVector = Icons.Default.Folder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Aset Information
            Column {
                Text(
                    text = aset.namaAset,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ID: ${aset.idAset}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}



