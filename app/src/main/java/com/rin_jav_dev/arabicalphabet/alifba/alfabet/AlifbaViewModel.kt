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
     var onSuccesLettersListener:OnSuccesLettersListener?=null
    var lettersList:ArrayList<Alif>?=null

    fun getLetters(){

        App.db.alifsModelDao().all
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).
            subscribe({

                if(it.size>0){
                    lettersList= it as ArrayList<Alif>
                    onSucces(it)

                }else{
                    lettersList= AlifsFactory.getAlifs()!!
                    App.db.alifsModelDao().insertAll(lettersList).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe({
                            onSucces(lettersList!!)
                        })}
            },
                {
                    lettersList= AlifsFactory.getAlifs()!!
                    App.db.alifsModelDao().
                    insertAll(lettersList)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            onSucces(lettersList!!)
                        })
                })
    }
    fun onSucces(alifs:List<Alif> ){
        onSuccesLettersListener?.lettersLoaded(alifs)
    }

    fun onError(tr:List<Alif> ){

    }
    interface OnSuccesLettersListener{
        fun lettersLoaded(alifs:List<Alif>);
    }
}