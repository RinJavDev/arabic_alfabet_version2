package com.rin_jav_dev.arabicalphabet.alifba.alfabet


import android.annotation.TargetApi
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.rin_jav_dev.arabicalphabet.R
import com.rin_jav_dev.arabicalphabet.alifba.AlifsFactory
import com.rin_jav_dev.arabicalphabet.database.alifs.Alif
import kotlinx.android.synthetic.main.alif_dialog_layout.*


class DialogFragmentAlif() :DialogFragment (){

        lateinit  var letter: Alif
         private  lateinit var  sp: SoundPool
        private val soundId = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        println("alif_position " + getArguments()?.getInt("alif_info"))
        getArguments()?.getInt("alif_position")?.let {letter= AlifsFactory.getAlifs()?.get(it)!! };
        val view = inflater.inflate(R.layout.alif_dialog_layout, container, false)

        activity?.setVolumeControlStream(AudioManager.STREAM_MUSIC)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool()
        } else {
          createOldSoundPool()
        }
       // sp.setOnLoadCompleteListener(SoundPool.OnLoadCompleteListener { sp, sampleId, status ->
       //     sp?.play(soundId, 1F, 1F, 0, 0, 1F)
       // })
        sp.load(context, letter.letterSoundId, 1)


        return view
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        tv_letter.text=letter.arabicLetter
        tvTransriprion.text=letter.trancsription
        tv_start_letter.text=letter.start
        tv_middle_letter.text=letter.mid
        tv_end_letter.text=letter.end
        btnCansel.setOnClickListener { dismiss() }
        btnPlaySoundOfLetter.setOnClickListener {   sp.play(soundId, 1F, 1F, 0, 0, 1F) }
    }


    }

