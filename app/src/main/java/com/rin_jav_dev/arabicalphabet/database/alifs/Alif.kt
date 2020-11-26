package com.rin_jav_dev.arabicalphabet.database.alifs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Alif")
open class  Alif(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "arabicLetter") val arabicLetter: String,
    @ColumnInfo(name = "trancsription") val trancsription: String,
    @ColumnInfo(name = "enableForAlpfabetTest") var enableForAlpfabetTest: Boolean,
    @ColumnInfo(name = "start") var start: String,
    @ColumnInfo(name = "mid") var mid: String,
    @ColumnInfo(name = "end") var end: String,
    @ColumnInfo(name = "letterSoundId") val letterSoundId: Int
) {


}