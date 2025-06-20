package com.uti.lockpocket_pm

data class RiwayatTabungan(
    val id: Int = 0,
    val bulan: String,
    val tanggal: String,
    val jumlah: Int,
    val keterangan: String,
    val jenis: String
)
