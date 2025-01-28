package com.example.finalprojectpam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectpam.ui.view.home.HomeView
import com.example.finalprojectpam.ui.view.pendapatan.InsertPendapatanView
import com.example.finalprojectpam.ui.view.pengeluaran.InsertPengeluaranView
import com.example.finalprojectpam.ui.viewmodel.Home.HomeViewModel

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier // Halaman awal adalah Home
    ) {
        // Halaman Home
        composable(route = DestinasiHome.route) {
            val homeViewModel: HomeViewModel = viewModel() // ViewModel untuk Home
            HomeView(
                viewModel = homeViewModel,
                onInsertPendapatan = { navController.navigate(DestinasiInsertPendapatan.route) },
                onInsertPengeluaran = { navController.navigate(DestinasiInsertPengeluaran.route) },
                onPagePengeluaran = { navController.navigate(DestinasiHomePengeluaran.route) },
                onPagePendapatan = { navController.navigate(DestinasiHomePendapatan.route) },
                onPageAset = { navController.navigate(DestinasiHomeAset.route) }
            )
        }

        // Halaman Insert Pendapatan
        composable(route = DestinasiInsertPendapatan.route) {
            val homeViewModel: HomeViewModel = viewModel() // Reuse HomeViewModel untuk saldo

            InsertPendapatanView(
                viewModelHome = homeViewModel,
                onBack = { navController.popBackStack() }, // Navigasi balik ke halaman sebelumnya
                onNavigate = { navController.popBackStack() } // Balik ke Home setelah insert
            )
        }

        // Halaman Insert Pengeluaran
        composable(route = DestinasiInsertPengeluaran.route) {
            val homeViewModel: HomeViewModel = viewModel() // Reuse HomeViewModel untuk saldo

            InsertPengeluaranView(
                viewModelHome = homeViewModel,
                onBack = { navController.popBackStack() }, // Navigasi balik ke halaman sebelumnya
                onNavigate = { navController.popBackStack() }, // Balik ke Home setelah insert
                onInsertPengeluaran = { navController.popBackStack() } // Balik setelah simpan
            )
        }
    }
}

