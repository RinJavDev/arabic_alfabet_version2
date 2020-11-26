package com.rin_jav_dev.arabicalphabet.alifba.alfabet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rin_jav_dev.arabicalphabet.alifba.AlifsFactory
import com.rin_jav_dev.arabicalphabet.app.App
import com.rin_jav_dev.arabicalphabet.database.alifs.Alif
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AlifbaViewModel : ViewModel() {

    private val _alifs = MutableLiveData<List<Alif>>().apply {
        App.db.alifsModelDao().all
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).
            subscribe({
                if(it.size>0){
                    value=it
                    onSucces(it)

                }else{
                    value= AlifsFactory.getAlifs()
                    App.db.alifsModelDao().insertAll(value).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe()}
                },
            {
                value= AlifsFactory.getAlifs()
                  App.db.alifsModelDao().
                         insertAll(value)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
            })
      // value =
      //     AlifsFactory.getAlifs()
    }

    fun onSucces(alifs:List<Alif> ){
    }

    fun onError(tr:List<Alif> ){

    }
    val alifs: LiveData<List<Alif>> = _alifs
}