package com.xxun.watch.picture_translate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.xxun.watch.picture_translate.R
import com.xxun.watch.picture_translate.databinding.ItemTranslateResultBinding
import com.xxun.watch.picture_translate.weight.SelectWordTextView

class TranslateResultAdapter(val list: List<String>, val listener: (String, String) -> Unit) :
    RecyclerView.Adapter<TranslateResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslateResultViewHolder {
        val binding =
            ItemTranslateResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TranslateResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TranslateResultViewHolder, position: Int) {
        holder.binding.viewActionSourceTxt.setOnClickWordListener {
            listener("", it)
        }
        holder.binding.viewActionTargetTxt.setOnClickWordListener {
            listener("", it)
        }
        holder.binding.viewActionPlayVoice.setOnClickListener {
            val voice = list[holder.bindingAdapterPosition]
            listener(voice, "")
        }
    }

    override fun getItemCount() = 30
}

class TranslateResultViewHolder(val binding: ItemTranslateResultBinding) :
    RecyclerView.ViewHolder(binding.root)