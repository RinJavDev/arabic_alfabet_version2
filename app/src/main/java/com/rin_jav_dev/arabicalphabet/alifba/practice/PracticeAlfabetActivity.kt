package com.rin_jav_dev.arabicalphabet.alifba.practice

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.DialogInterface
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import com.rin_jav_dev.arabicalphabet.R
import com.rin_jav_dev.arabicalphabet.database.alifs.Alif
import com.rin_jav_dev.arabicalphabet.databinding.ActivityPracticeAlfabetBinding
import kotlinx.android.synthetic.main.activity_practice_alfabet.*

class PracticeAlfabetActivity : AppCompatActivity(),AlfabetPracticeViewModel.OpenNewLetter {
    private lateinit var practiceViewModel: AlfabetPracticeViewModel
    private  lateinit var  sp: SoundPool
    private val soundId = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bindig: ActivityPracticeAlfabetBinding = DataBindingUtil.setContentView(this,R.layout.activity_practice_alfabet)
        practiceViewModel =ViewModelProvider(this).get(AlfabetPracticeViewModel::class.java)
        practiceViewModel.openNewLetterListener=this;
        bindig.viewModel=practiceViewModel
            setTitle(R.string.practice)

        setVolumeControlStream(AudioManager.STREAM_MUSIC)
        tvArabicLetter.setOnClickListener {  sp.play(soundId, 1F, 1F, 0, 0, 1F) }
    }

    override fun onStart() {
        super.onStart()
        practiceViewModel.soundId.observe(this, Observer {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                createNewSoundPool()
            } else {
                createOldSoundPool()
            }
            sp.setOnLoadCompleteListener(SoundPool.OnLoadCompleteListener { sp, sampleId, status ->
                sp?.play(soundId, 1F, 1F, 0, 0, 1F)
            })
        if(it!=null)sp.load(this, it, 1)
        })


        practiceViewModel.missings.observe(this, Observer {
            ivMiss1.visibility= View.INVISIBLE
            ivMiss2.visibility= View.INVISIBLE
            ivMiss3.visibility= View.INVISIBLE
            if(it>0) ivMiss1.visibility= View.VISIBLE
            if(it>1) ivMiss2.visibility= View.VISIBLE
            if(it>2) {
                ivMiss3.visibility= View.VISIBLE
                showStopTest()

            }

        })

        practiceViewModel.finishTest.observe(this, Observer {
            println("finishTest")
       if(it!=null)finishTest(it)

        })

    }

    private fun finishTest(result:String) {
        val alertDialogBuilder =
            AlertDialog.Builder(this)
        alertDialogBuilder.setTitle( getString(R.string.finish))
        alertDialogBuilder
            .setMessage(getString(R.string.result)+": "+result)
            .setCancelable(false)
            .setPositiveButton(
                getString(R.string.ok)
            ) { dialog: DialogInterface, id: Int ->

                dialog.cancel()
                finish()
            }

            .create().show()

        showAppReview()
    }

    fun showStopTest(){
        val alertDialogBuilder =
            AlertDialog.Builder(this)
        alertDialogBuilder.setTitle( getString(R.string.failed))
        alertDialogBuilder
            .setMessage(getString(R.string.made_3_mistakes))
            .setCancelable(false)
            .setPositiveButton(
                getString(R.string.ok)
            ) { dialog: DialogInterface, id: Int ->

                dialog.cancel()
                finish()
            }

            .create().show()
    }
    fun showAppReview(){
        val manager = ReviewManagerFactory.create(this)
        val request: Task<ReviewInfo> = manager.requestReviewFlow()
        request.addOnCompleteListener({ task ->
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                val reviewInfo: ReviewInfo = task.getResult()
                val flow = manager.launchReviewFlow(
                    this, reviewInfo
                )
                flow.addOnCompleteListener { task: Task<Void?>? -> println("task?.isComplete "+task?.isComplete) }
            } else {
                println("task?.isComplete no down")
                // There was some problem, continue regardless of the result.
            }
        })
    }
    @SuppressWarnings("deprecation")
    fun createOldSoundPool(){
        sp = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createNewSoundPool() {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        sp = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .build()
    }

    override fun onOpenNewLetter(alif: Alif) {
        setResult(10)
        println("Открыта новая Буква "+alif.trancsription)
    }
}