package com.uti.lockpocket_pm

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseTabungan(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "tabungan.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "riwayat_tabungan"
        const val COLUMN_ID = "id"
        const val COLUMN_BULAN = "bulan"
        const val COLUMN_JUMLAH = "jumlah"
        const val COLUMN_KETERANGAN = "keterangan"
        const val COLUMN_TIPE = "tipe"
        const val COLUMN_TANGGAL = "tanggal"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_BULAN TEXT,
                $COLUMN_JUMLAH INTEGER,
                $COLUMN_KETERANGAN TEXT,
                $COLUMN_TIPE TEXT,
                $COLUMN_TANGGAL TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun simpanTransaksi(
        bulan: String,
        jumlah: Int,
        keterangan: String,
        tipe: String,
        tanggal: String
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_BULAN, bulan)
            put(COLUMN_JUMLAH, jumlah)
            put(COLUMN_KETERANGAN, keterangan)
            put(COLUMN_TIPE, tipe)
            put(COLUMN_TANGGAL, tanggal)
        }
        val result = db.insert(TABLE_NAME, null, values)

        Log.d("DatabaseTabungan", "INSERT -> bulan=$bulan | jumlah=$jumlah | keterangan=$keterangan | tipe=$tipe | tanggal=$tanggal | result=$result")

        db.close()
        return result != -1L
    }

    fun getTransaksiByBulan(bulan: String): List<TransaksiModel> {
        val transaksiList = mutableListOf<TransaksiModel>()
        val db = readableDatabase

        Log.d("DatabaseTabungan", "SELECT -> Ambil transaksi bulan: $bulan")

        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_BULAN = ?",
            arrayOf(bulan),
            null,
            null,
            "$COLUMN_ID DESC"
        )

        if (cursor.moveToFirst()) {
            do {
                val jumlah = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_JUMLAH))
                val keterangan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KETERANGAN))
                val tipe = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPE))
                val tanggal = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TANGGAL))

                transaksiList.add(TransaksiModel(jumlah, keterangan, tipe, tanggal))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return transaksiList
    }

    fun getTotalByTipeAndBulan(tipe: String, bulan: String): Int {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT SUM($COLUMN_JUMLAH) FROM $TABLE_NAME WHERE $COLUMN_TIPE = ? AND $COLUMN_BULAN = ?",
            arrayOf(tipe, bulan)
        )

        var total = 0
        if (cursor.moveToFirst()) {
            total = cursor.getInt(0)
        }

        cursor.close()
        db.close()
        return total
    }

    fun hapusSemuaTransaksi() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    fun logSemuaData() {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val bulan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BULAN))
                val tipe = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPE))
                val jumlah = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_JUMLAH))
                val tanggal = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TANGGAL))
                val ket = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KETERANGAN))
                Log.d("DATA_SEMUA", "id=$id | bulan=$bulan | tipe=$tipe | jumlah=$jumlah | tanggal=$tanggal | ket=$ket")
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
    }
}

data class TransaksiModel(
    val jumlah: Int,
    val keterangan: String,
    val tipe: String,
    val tanggal: String
)
