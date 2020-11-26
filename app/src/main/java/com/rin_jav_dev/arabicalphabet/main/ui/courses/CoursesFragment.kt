package com.rin_jav_dev.arabicalphabet.main.ui.courses

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rin_jav_dev.arabicalphabet.R
import com.rin_jav_dev.arabicalphabet.alifba.alfabet.AlifbaActivity
import kotlinx.android.synthetic.main.fragment_courses.*


class CoursesFragment : Fragment() {

    private lateinit var coursesViewModel: CoursesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coursesViewModel =
            ViewModelProvider(this).get(CoursesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_courses, container, false)
      //  val textView: TextView = root.findViewById(R.id.text_notifications)


        coursesViewModel.text.observe(viewLifecycleOwner, Observer {
         //   textView.text = it
        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        alifbaLayout.setOnClickListener { startActivity(Intent(activity, AlifbaActivity::class.java)) }
        alifbaLayout.setOnTouchListener(object :View.OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                when (p1?.getAction()) {
                    MotionEvent.ACTION_DOWN -> {
                        alifbaLayout.transitionToEnd();
                    }

                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        alifbaLayout.transitionToStart()
                    }
                }
             return false
            }

        })

    }
}