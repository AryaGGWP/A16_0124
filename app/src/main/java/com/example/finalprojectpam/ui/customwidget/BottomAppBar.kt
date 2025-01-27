package com.example.finalprojectpam.ui.customwidget

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pertemuan12.R

@Preview
@Composable
fun BottomAppBar(
    onHomeClick: () -> Unit = {},
    onPageAset: () -> Unit = {},
    onPageKategori: () -> Unit = {},
    onPagePendapatan: () -> Unit = {},
    onPagePengeluaran: () -> Unit = {},
    showHomeClick: Boolean = true,
    showPageAset: Boolean = true,
    showPageKategori: Boolean = true,
    showPagePendapatan: Boolean = true,
    showPagePengeluaran: Boolean = true
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        contentAlignment = Alignment.Center
    ){
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            color = MaterialTheme.colorScheme.primary,
            shadowElevation = 10.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (showPageAset) {
                    IconButton(onClick = onPageAset,
                        modifier = Modifier.
                        size(35.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.aset),
                            contentDescription = "Aset"
                        )
                    }
                }
                if (showPagePendapatan) {
                    IconButton(onClick = onPagePendapatan,
                        modifier = Modifier.
                        size(40.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.kategori),
                            contentDescription = "Kategori"
                        )
                    }
                }
                if(showHomeClick){
                    IconButton(onClick = onHomeClick,
                        modifier = Modifier.
                        size(50.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "Home"
                        )
                    }
                }
                if (showPagePengeluaran) {
                    IconButton(onClick = onPagePengeluaran,
                            modifier = Modifier.
                            size(35.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.pengeluaran),
                            contentDescription = "Pengeluaran"
                        )
                    }
                }
                if (showPageKategori) {
                    IconButton(onClick = onPageKategori,
                        modifier = Modifier.
                        size(35.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.pendapatan),
                            contentDescription = "Pendapatan"
                        )
                    }
                }
            }
        }
    }
}
