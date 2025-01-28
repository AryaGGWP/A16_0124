package com.example.finalprojectpam.ui.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalprojectpam.ui.customwidget.BottomAppBar
import com.example.finalprojectpam.ui.customwidget.CustomTopAppBar
import com.example.finalprojectpam.ui.viewmodel.Home.HomeViewModel
import com.example.finalprojectpam.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    viewModel: HomeViewModel,
    onPageAset: () -> Unit = {},
    onPageKategori: () -> Unit = {},
    onPagePendapatan: () -> Unit = {},
    onPagePengeluaran: () -> Unit = {},
    onInsertPendapatan: () -> Unit = {},
    onInsertPengeluaran: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                onBack = {},
                judul = "Aplikasi Keuangan",
                judulKecil = "Halo, Selamat Datang",
                showBackButton = false,
                showProfile = true,
                showJudulKecil = true,
                showSaldo = true,
                modifier = modifier,
                homeViewModel = viewModel
            )
        },
        bottomBar = {
            BottomAppBar(
                onHomeClick = {},
                onPageAset = onPageAset,
                onPageKategori = onPageKategori,
                onPagePendapatan = onPagePendapatan,
                onPagePengeluaran = onPagePengeluaran,
                showHomeClick = true,
                showPageAset = true,
                showPageKategori = true,
                showPagePendapatan = true,
                showPagePengeluaran = true
            )
        }
    ) { innerPadding ->
        // Kolom utama
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center // Posisikan konten di tengah layar
        ) {
            // Row untuk tombol
            Row(
                modifier = Modifier.wrapContentSize(), // Ukuran sesuai konten
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Spasi antar tombol
            ) {
                // IconButton Insert Pendapatan
                IconButton(
                    onClick = onInsertPendapatan,
                    modifier = Modifier
                        .size(80.dp) // Ukuran tombol
                        .clip(RoundedCornerShape(16.dp)) // Bentuk rounded corner
                        .background(Color(0xFF4CAF50)) // Warna hijau
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.tambahpendapatan),
                        contentDescription = "Insert Pendapatan",
                        modifier = Modifier.size(60.dp) // Ukuran icon
                    )
                }
                // IconButton Insert Pengeluaran
                IconButton(
                    onClick = onInsertPengeluaran,
                    modifier = Modifier
                        .size(80.dp) // Ukuran tombol
                        .clip(RoundedCornerShape(16.dp)) // Bentuk rounded corner
                        .background(Color(0xFFF44336)) // Warna merah
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.tambahpengeluaran), // Icon pengeluaran
                        contentDescription = "Insert Pengeluaran",
                        modifier = Modifier.size(60.dp) // Ukuran icon
                    )
                    Text(
                        text = "Tambah Pengeluaran",
                        color = Color.White,
                        fontSize = 25.sp,
                    )
                }
            }
        }
    }
}
