 package com.mne4.myfirstapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mne4.myfirstapplication.databinding.ActivityMainBinding
import org.json.JSONObject


 class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
    fun onClickSearchButton(view: View) {
        getData(binding.editTextNumber.text.toString())
     }

     private fun getData(bin: String){
         val url = "https://lookup.binlist.net/${bin.split(' ').joinToString("")}"
         val queue = Volley.newRequestQueue(this)
         val stringRequest = StringRequest(Request.Method.GET, url,
             { response ->

                 val obj = JSONObject(response)
                 val card = CardInfo(obj)

                 binding.textViewError.text = ""
                 binding.prepaidTextView.text = card.prepaid
                 binding.brandTextView.text = card.brand
                 binding.typeTextView.text = card.type
                 binding.schemeTextView.text = card.scheme
                 binding.lengthTextView.text = card.number.length
                 binding.luhinTextView.text = card.number.luhn
                 binding.textViewBankName.text = "${card.bank.name}, ${card.bank.city}"
                 binding.textViewBankLink.text = card.bank.url
                 binding.textViewBankPhone.text = card.bank.phone
                 binding.textViewCountryName.text = card.country.name
                 binding.textViewLatLong.text = "(latitude: ${card.country.latitude}, longitude: ${card.country.longitude})"

             },
             {
                 binding.textViewError.text = "Информация по данному BIN/IIN не найдена"
             })
         queue.add(stringRequest)
     }
 }