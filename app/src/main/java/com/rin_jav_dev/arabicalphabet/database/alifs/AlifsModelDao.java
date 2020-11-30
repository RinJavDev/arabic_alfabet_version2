package com.rin_jav_dev.arabicalphabet.database.alifs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface AlifsModelDao {
    @Query("SELECT * FROM Alif")
    Maybe<List<Alif>> getAll();

    @Query("SELECT * FROM Alif WHERE enableForAlpfabetTest ")
    Maybe<List<Alif>> getOpened();

    @Query("SELECT * FROM Alif WHERE NOT enableForAlpfabetTest ")
    Maybe<Alif> getNotOpenLetter();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<Alif> alifs);
    @Insert
    void insert(Alif caseAudioModel);
    @Update
    Completable  update(Alif caseAudioModel);

  //  @Query("DELETE FROM Alif WHERE Alif IN (:envelope_id)")
  //  void deleteItem(Long envelope_id);

    @Query("DELETE FROM Alif")
    void clearData();
}
