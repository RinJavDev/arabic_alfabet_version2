
package com.rin_jav_dev.arabicalphabet.main.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.rin_jav_dev.arabicalphabet.Login_Activity
import com.rin_jav_dev.arabicalphabet.R
import com.rin_jav_dev.arabicalphabet.app.SharedRepository
import com.rin_jav_dev.arabicalphabet.databinding.ProfileFragmentBinding
import kotlinx.android.synthetic.main.profile_fragment.*


class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }


    var binding: ProfileFragmentBinding?=null
    private lateinit var viewModel: ProfileViewModel
    private lateinit var mRewardedVideoAd: RewardedVideoAd
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding = ProfileFragmentBinding.inflate(inflater, container, false)

        return binding!!.root
        //inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        viewModel.goSignOut.observe(viewLifecycleOwner, Observer {
            FirebaseAuth.getInstance().signOut();
            val googleSignInClient =
                activity?.let { it1 ->
                    GoogleSignIn.getClient(
                        it1, GoogleSignInOptions.Builder(
                            GoogleSignInOptions.DEFAULT_SIGN_IN
                        )
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build()
                    )
                }
            googleSignInClient?.signOut()

            startActivity(Intent(activity, Login_Activity::class.java))
        })

        binding?.viewModel=viewModel
       // viewModel._user.observe(viewLifecycleOwner, Observer {
       //     println("userName changed")
       //     binding?.viewModel=viewModel //<- re-binding user
       // })
        // TODO: Use the ViewModel
        btnWatchAds.setOnClickListener {
            if (mRewardedVideoAd.isLoaded) {
                mRewardedVideoAd.show()
            }else {
                Toast.makeText(
                    context,
                    getString(R.string.check_internet_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(activity)

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity)

      if(! mRewardedVideoAd.isLoaded) loadRewardedVideoAd()
    }


    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(
            SharedRepository.getVideoReclamIdentify(),
            AdRequest.Builder().build()
        )
    }

    override fun onPause() {
        super.onPause()
        mRewardedVideoAd.pause(activity)
    }

    override fun onResume() {
        super.onResume()
        mRewardedVideoAd.resume(activity)


    }

    override fun onDestroy() {
        super.onDestroy()
       mRewardedVideoAd.destroy(activity)
    }
}