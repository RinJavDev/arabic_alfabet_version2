package com.rin_jav_dev.arabicalphabet.alifba.alfabet

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.rin_jav_dev.arabicalphabet.R
import com.rin_jav_dev.arabicalphabet.alifba.practice.PracticeAlfabetActivity
import com.rin_jav_dev.arabicalphabet.app.App
import com.rin_jav_dev.arabicalphabet.app.SharedRepository
import com.rin_jav_dev.arabicalphabet.database.alifs.Alif
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_alifba.*


class AlifbaActivity : AppCompatActivity(),AlifbaViewModel.OnSuccesLettersListener {
    private lateinit var alifbaViewModel: AlifbaViewModel
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alifba)


        alifbaViewModel =
            ViewModelProvider(this).get(AlifbaViewModel::class.java)
        alifbaViewModel.onSuccesLettersListener=this
        alifbaViewModel.getLetters()
        btnPractice.setOnClickListener {
            App.db.alifsModelDao().opened.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe({
                    if (it.size >= 4) {
                        startActivityForResult(Intent(this, PracticeAlfabetActivity::class.java), 0)
                        if (mInterstitialAd.isLoaded) {
                            mInterstitialAd.show()
                        }
                    } else {
                        Toast.makeText(
                            this,
                            getString(R.string.choose_more_3_leters),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, {})


        }

    }

    override fun onResume() {
        super.onResume()
        //alifbaViewModel.alifs.
        MobileAds.initialize(this) {}
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = SharedRepository.getStartPracticeIdentify()
        mInterstitialAd.loadAd(AdRequest.Builder().build())



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.alifba_menu, menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId.equals(R.id.show_tutorial)){

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.alifba_tutorial_title_for_dialog))
                .setMessage(getString(R.string.alifba_tutorial_text_for_dialog))
                //.setIcon(R.drawable.ic_launcher_cat)
                .setPositiveButton(
                    getString(R.string.ok),
                    DialogInterface.OnClickListener { dialog, id -> // Закрываем окно
                        dialog.cancel()
                    })
            builder.create().show()
            println("show tutorial")
            return true
        }

        return super.onOptionsItemSelected(item)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode.equals(10)){
            alifbaViewModel.getLetters()
        }
    }
    override fun lettersLoaded(alifs: List<Alif>) {
        rv_alifs.layoutManager =
            (GridLayoutManager(this, 4))
        rv_alifs.adapter =
            ArabicLettersAdapter(
                alifs,
                object :
                    ArabicLettersAdapter.Callback {
                    override fun onItemClicked(item: Alif, position: Int) {

                        // item.enableForAlpfabetTest = !item.enableForAlpfabetTest
                        // App.db.alifsModelDao().update(item)
                        //     .subscribeOn(Schedulers.io())
                        //     .observeOn(AndroidSchedulers.mainThread())
                        //     .subscribe()
                        // rv_alifs.adapter!!.notifyItemChanged(position)

                        val fm: FragmentManager = supportFragmentManager
                        val alifDialogFragmentAlif =
                            DialogFragmentAlif()
                        val args = Bundle()
                        println("alif_position " + position)
                        args.putInt("alif_position", position)
                        alifDialogFragmentAlif.arguments = args
                        alifDialogFragmentAlif.show(fm, "alif_info")

                    }

                    override fun onInfoClicked(item: Alif, position: Int) {
                        item.enableForAlpfabetTest = !item.enableForAlpfabetTest
                        App.db.alifsModelDao().update(item)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                        rv_alifs.adapter!!.notifyItemChanged(position)
                    }

                })
    }


}