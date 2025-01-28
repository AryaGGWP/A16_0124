package com.example.finalprojectpam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Aset(
    @SerialName("id_aset")
    val idAset: Int,
    @SerialName("nama_aset")
    val namaAset: String
)

@Serializable
data class AllAsetResponse(
    val data: List<Aset>,
    val message: String,
    val status: Boolean
)

@Serializable
data class AsetDetail(
    val data: Aset,
    val message: String,
    val status: Boolean
)

@Serializable
data class Kategori(
    @SerialName("id_kategori")
    val idKategori: Int,
    @SerialName("nama_kategori")
    val namaKategori: String
)

@Serializable
data class AllKategoriResponse(
    val data: List<Kategori>,
    val message: String,
    val status: Boolean
)

@Serializable
data class KategoriDetail(
    val data: Kategori,
    val message: String,
    val status: Boolean
)

@Serializable
data class Pendapatan(
    @SerialName("id_pendapatan") val idPendapatan: Int,
    @SerialName("id_aset") val idAset: Int,
    @SerialName("id_kategori") val idKategori: Int,
    @SerialName("tanggal_transaksi") val tanggalTransaksi: String,
    val total: Double,
    val catatan: String,
    @SerialName("nama_aset") val namaAset: String? = null,       // Tambahkan properti ini
    @SerialName("nama_kategori") val namaKategori: String? = null // Tambahkan properti ini
)

@Serializable
data class AllPendapatanResponse(
    val data: List<Pendapatan>,
    val message: String,
    val status: Boolean
)

@Serializable
data class PendapatanDetail(
    val data: Pendapatan,
    val message: String,
    val status: Boolean
)

@Serializable
data class Pengeluaran(
    @SerialName("id_pengeluaran") val idPengeluaran: Int,
    @SerialName("id_aset") val idAset: Int,
    @SerialName("id_kategori") val idKategori: Int,
    @SerialName("tanggal_transaksi") val tanggalTransaksi: String,
    val total: Double,
    val catatan: String
)
@Serializable
data class AllPengeluaranResponse(
    val data: List<Pengeluaran>,
    val message: String,
    val status: Boolean
)

@Serializable
data class PengeluaranDetail(
    val data: Pengeluaran,
    val message: String,
    val status: Boolean
)

@Serializable
data class ManajemenKeuanganResponse(
    val saldo: Double,
    @SerialName("total_pendapatan")
    val totalPendapatan: Double,
    @SerialName("total_pengeluaran")
    val totalPengeluaran: Double,
    val status: Boolean
)
