package com.example.finalprojectpam.ui.customwidget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalprojectpam.ui.viewmodel.Home.HomeViewModel
import com.example.finalprojectpam.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    onBack: () -> Unit,
    judul: String,
    judulKecil: String,
    showBackButton: Boolean = true,
    showProfile: Boolean = true,
    showJudulKecil: Boolean = true,
    showSaldo: Boolean = true
) {
    val saldo by homeViewModel.saldo.collectAsState()
    val totalPengeluaran by homeViewModel.totalPengeluaran.collectAsState()
    val totalPendapatan by homeViewModel.totalPendapatan.collectAsState()
    Column(
        modifier = modifier.padding(top = 16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background
                    (brush = Brush.verticalGradient
                    (colors = listOf(Color(0xFF6650a4),
                    Color(0xFFEFB8C8)
                    )))
        ) {
            Surface(
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, bottom = 8.dp, end = 16.dp)
                        .width(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    if (showBackButton) {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.align(Alignment.CenterVertically))
                    {
                        Text(
                            text =  judul,
                            color = colorResource(id = R.color.gold),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal
                        )
                        if (showJudulKecil) {
                            Text(
                                text =  judulKecil,
                                color = colorResource(id = R.color.white),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            )
                        }
                    }
                    if (showProfile){
                        Image(
                            painter = painterResource(id = R.drawable.yey),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(125.dp)
                                .padding(start = 8.dp, top = 6.dp, bottom = 6.dp)
                                .clip(shape = CircleShape,)
                                .border(width = 3.dp,
                                    color = MaterialTheme.colorScheme.primary
                                    ,shape = CircleShape)
                                .background(color = Color.White)
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                if (showSaldo) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.elevatedCardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Saldo Saat Ini",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = if (saldo >= 0)"Rp. $saldo" else " (-) Rp. $saldo",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = if (saldo >= 0) Color.Green else Color.Red
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Card Total Pengeluaran
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.elevatedCardElevation(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Total Pengeluaran",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Rp. $totalPengeluaran",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.Red
                                    )
                                }
                            }

                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.elevatedCardElevation(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Total Pendapatan",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Rp. $totalPendapatan",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.Green
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
