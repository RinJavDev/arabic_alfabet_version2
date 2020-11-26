//package com.rin_jav_dev.arabicalphabet.database.alifs;
//
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.Ignore;
//import androidx.room.PrimaryKey;
//
//
//
//@Entity(tableName = "caseAudioModel")
//
//public class CaseAudioModel {
//
//    @PrimaryKey(autoGenerate = true)
//
//    Long audioId;
//    @ColumnInfo(name = "envelope_id")
//
//    Long envelopeId;
//    @ColumnInfo(name = "addinfo_id")
//
//    Long addInfoId;
//    @ColumnInfo(name = "filename")
//
//    String fileName;
//
//    public CaseAudioModel(Long envelopeId, Long addInfoId, String fileName) {
//        this.envelopeId = envelopeId;
//        this.addInfoId = addInfoId;
//        this.fileName = fileName;
//    }
//    @Ignore
//    public CaseAudioModel() {
//    }
//
//    public Long getEnvelopeId() {
//        return envelopeId;
//    }
//
//    public Long getAddInfoId() {
//        return addInfoId;
//    }
//
//    public Long getAudioId() {
//        return audioId;
//    }
//
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setEnvelopeId(Long envelopeId) {
//        this.envelopeId = envelopeId;
//    }
//
//    public void setAddInfoId(Long addInfoId) {
//        this.addInfoId = addInfoId;
//    }
//
//    public void setAudioId(Long audioId) {
//        this.audioId = audioId;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }
//}
