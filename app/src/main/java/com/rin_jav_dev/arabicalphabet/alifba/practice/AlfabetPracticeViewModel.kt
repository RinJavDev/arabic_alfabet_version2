package com.rin_jav_dev.arabicalphabet.alifba.practice

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.rin_jav_dev.arabicalphabet.app.App
import com.rin_jav_dev.arabicalphabet.app.SharedRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class AlfabetPracticeViewModel : ViewModel() {
    var rightTranscription=""
    var  arabicLetter: ObservableField<String> = ObservableField()
    var  progress: ObservableField<String> = ObservableField()
    var  testPostition=0;
    var  maxPosition=SharedRepository.getMaxTestPosotionAlfabet();
    var  btnTest1: ObservableField<String> = ObservableField()
    var  btnTest2: ObservableField<String> = ObservableField()
    var  btnTest3: ObservableField<String> = ObservableField()
    var  btnTest4: ObservableField<String> = ObservableField()

     var missings  = MutableLiveData<Int>().apply {
        value = 0
    }
     var  finishTest   = MutableLiveData<String>().apply {
         value =null
     }
    var  soundId   = MutableLiveData<Int>().apply {
        value =null
    }
        init {
           // AlifsFactory.doFilterTestAlifs()

            nextQuestion()
        }
     @SuppressLint("CheckResult")
     fun nextQuestion(){
         App.db.alifsModelDao().opened.subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread()).
             subscribe({
                 val lettes=it
                 lettes.shuffle()
                 btnTest1.set(lettes.get(0).trancsription)
                 btnTest2.set(lettes.get(1).trancsription)
                 btnTest3.set(lettes.get(2).trancsription)
                 btnTest4.set(lettes.get(3).trancsription)

                 val random = Random().nextInt(4)
                 arabicLetter.set(lettes.get(random).arabicLetter);
                 rightTranscription = lettes.get(random).trancsription
                 println("nextQuestion "+lettes.get(random).trancsription)
                 soundId.value=lettes.get(random).letterSoundId
                  },{})

        println("nextQuestion")

    }

     fun answer(view:View){
        // progress.set(progress.get()!! +1)
        // println("answer is "+ (view as Button).text.toString() +" "+ rightTranscription)

        if((view as Button).text.toString().equals(rightTranscription)){
            println("answer is right")
        }else{
            println("answer isn't right")
            missings.value= missings.value!! +1
            println("answer missings value = "+ missings.value)
        }


         testPostition++
         progress.set(testPostition.toString()+"/"+maxPosition)
         if(testPostition<maxPosition){
             nextQuestion()
         }else{
            finishTest()
         }

    }

    private fun finishTest() {
        finishTest.value=(maxPosition- missings.value!!).toString()+"/"+maxPosition

    }


    // private val _alifs = MutableLiveData<ArrayList<Alif>>().apply {
    //     value = AlifsFactory.getAlifs()
    // }
    // val alifs: LiveData<ArrayList<Alif>> = _alifs
}