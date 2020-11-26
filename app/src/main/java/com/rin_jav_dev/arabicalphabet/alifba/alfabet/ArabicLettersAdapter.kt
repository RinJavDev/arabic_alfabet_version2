package com.rin_jav_dev.arabicalphabet.alifba.alfabet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rin_jav_dev.arabicalphabet.R
import com.rin_jav_dev.arabicalphabet.database.alifs.Alif

class ArabicLettersAdapter(var items: List<Alif>, val callback: Callback) : RecyclerView.Adapter<ArabicLettersAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.letter_item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(items[position],position)
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val arabicLetter = itemView.findViewById<TextView>(R.id.tvArabicLetter)
        private val trancrption = itemView.findViewById<TextView>(R.id.tvTranscription)
        private val backGround = itemView.findViewById<ImageView>(R.id.iv_backGround)
        private val btnInfo = itemView.findViewById<Button>(R.id.btnLetterInfo)
        fun bind(item: Alif, position: Int) {
            if(item.enableForAlpfabetTest) backGround.setImageResource( R.drawable.letter_enable_background) else backGround.setImageResource( R.drawable.letter_background)
            arabicLetter.text = item.arabicLetter
            trancrption.text = item.trancsription
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition],position )
            }
            btnInfo.setOnClickListener {  if (adapterPosition != RecyclerView.NO_POSITION) callback.onInfoClicked(position) }
        }
    }

   public interface Callback {
        fun onItemClicked(item: Alif, position: Int)
        fun onInfoClicked(position: Int)
    }

}