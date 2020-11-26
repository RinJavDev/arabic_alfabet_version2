
package com.rin_jav_dev.arabicalphabet.main.ui.profile

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rin_jav_dev.arabicalphabet.R
import com.squareup.picasso.Picasso


class ProfileViewModel : ViewModel() {

    private val _showSignOut = MutableLiveData<Boolean?>()
    val goSignOut: LiveData<Boolean?>
        get() = _showSignOut
    var  userName:ObservableField<String> =ObservableField()


    var  email:ObservableField<String> = ObservableField()
    var  photoUrl:ObservableField<String> = ObservableField()



    init {
      val currentUser= FirebaseAuth . getInstance().currentUser
        if(currentUser!=null){
        userName.set(currentUser.displayName)
            email.set(currentUser.email)
            photoUrl.set(currentUser.photoUrl.toString())
        }

    }


    fun getImageUrl(): String? {
        // The URL will usually come from a model (i.e Profile)
        return photoUrl.get()
    }

    companion object{
        @JvmStatic
        @BindingAdapter("bind:imageUrl")
        fun loadImage(view: ImageView, imageUrl: String?) {
            Picasso.with(view.context)
                .load(imageUrl)
                //.placeholder(R.drawable.placeholder)
                .into(view)
        }

    }


    fun signOut(){

        _showSignOut.value = true
    }
}
