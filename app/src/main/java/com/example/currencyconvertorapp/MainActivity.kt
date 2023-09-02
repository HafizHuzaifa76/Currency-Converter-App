package com.example.currencyconvertorapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconvertorapp.api.ApiInterface
import com.example.currencyconvertorapp.api.ApiUtilities
import com.example.currencyconvertorapp.databinding.ActivityMainBinding
import com.example.currencyconvertorapp.repository.CurrencyRepository
import com.example.currencyconvertorapp.room.CurrencyDatabase
import com.example.currencyconvertorapp.viewmodel.CurrencyViewModel
import com.example.currencyconvertorapp.viewmodel.CurrencyViewModelFactory

class MainActivity : AppCompatActivity() {
    val currencyNamesArrayList = ArrayList<String>()
    lateinit var binding:ActivityMainBinding
    lateinit var currencyViewModel: CurrencyViewModel
    var selectedCurrency1 = ""
    var selectedCurrencyValue1:Double = 1.0
    var selectedCurrency2 = ""
    var selectedCurrencyValue2:Double = 1.0
    var finalCurrencyValue1:Double = 0.0
    var finalCurrencyValue2:Double = 0.0
    val context:Context = this
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        var flag = 0
        val database = CurrencyDatabase.getInstance(this)
        val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)
        val currencyRepository = CurrencyRepository(apiInterface,database,this)

        currencyViewModel = ViewModelProvider(this,CurrencyViewModelFactory(currencyRepository)).get(CurrencyViewModel::class.java)

        currencyViewModel.currData.observe(this, Observer {
            Log.d("CurrencyConvertor", "onCreate: ${it.toString()}")
        })

        currencyNamesArrayList.add("USD"); currencyNamesArrayList.add("AUD"); currencyNamesArrayList.add("BGN")
        currencyNamesArrayList.add("BRL"); currencyNamesArrayList.add("CAD"); currencyNamesArrayList.add("CHF")
        currencyNamesArrayList.add("CNY"); currencyNamesArrayList.add("CZK"); currencyNamesArrayList.add("DKK")
        currencyNamesArrayList.add("EUR"); currencyNamesArrayList.add("GBP"); currencyNamesArrayList.add("HKD")
        currencyNamesArrayList.add("HRK"); currencyNamesArrayList.add("HUF"); currencyNamesArrayList.add("ILS")
        currencyNamesArrayList.add("INR"); currencyNamesArrayList.add("ISK"); currencyNamesArrayList.add("JPY")
        currencyNamesArrayList.add("KRW"); currencyNamesArrayList.add("MXN"); currencyNamesArrayList.add("MYR")
        currencyNamesArrayList.add("NOK"); currencyNamesArrayList.add("NZD"); currencyNamesArrayList.add("PHP")
        currencyNamesArrayList.add("PLN"); currencyNamesArrayList.add("RON"); currencyNamesArrayList.add("RUB")
        currencyNamesArrayList.add("SEK"); currencyNamesArrayList.add("SGD"); currencyNamesArrayList.add("THB")
        currencyNamesArrayList.add("TRY"); currencyNamesArrayList.add("ZAR")
        val spinnerAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,currencyNamesArrayList)


        binding.currency1Spinner.adapter = spinnerAdapter
        binding.currency1Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCurrency1 = currencyNamesArrayList[position]
                flag = 1
                currencyViewModel.updatedCurrency(selectedCurrency1)
//                binding.convertedCurrency1TextView.text = "1 $selectedCurrency1 = $selectedCurrencyValue2 $selectedCurrency2"
                binding.currency1TextView.text = selectedCurrency1
                binding.currency1TextView2.text = "$selectedCurrency1:"
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // This method is called when nothing is selected
            }
        }
        binding.currency2Spinner.adapter = spinnerAdapter
        binding.currency2Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCurrency2 = currencyNamesArrayList[position]
                flag = 2
                currencyViewModel.updatedCurrency(selectedCurrency2)
                binding.currency2TextView.text = selectedCurrency2
                binding.currency2TextView2.text = "$selectedCurrency2:"
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // This method is called when nothing is selected
            }
        }

        currencyViewModel.currencyVal.observe(this, Observer {
            Log.d("observe",it.toString())
            if (flag == 1) {
                selectedCurrencyValue1 = it
                binding.acButtonEditText1.performClick()
                finalCurrencyValue1 = selectedCurrencyValue2/selectedCurrencyValue1
                finalCurrencyValue1 = String.format("%.4f", finalCurrencyValue1).toDouble()
                finalCurrencyValue2 = selectedCurrencyValue1/selectedCurrencyValue2
                finalCurrencyValue2 = String.format("%.4f", finalCurrencyValue2).toDouble()
                binding.convertedCurrency1TextView.text = "1 $selectedCurrency1 = $finalCurrencyValue1 $selectedCurrency2"
                binding.convertedCurrency2TextView.text = "1 $selectedCurrency2 = $finalCurrencyValue2 $selectedCurrency1"
            }
            else if (flag == 2) {
                selectedCurrencyValue2 = it
                binding.acButtonEditText1.performClick()
                finalCurrencyValue1 = selectedCurrencyValue2/selectedCurrencyValue1
                finalCurrencyValue1 = String.format("%.4f", finalCurrencyValue1).toDouble()
                finalCurrencyValue2 = selectedCurrencyValue1/selectedCurrencyValue2
                finalCurrencyValue2 = String.format("%.4f", finalCurrencyValue2).toDouble()
                binding.convertedCurrency1TextView.text = "1 $selectedCurrency1 = $finalCurrencyValue1 $selectedCurrency2"
                binding.convertedCurrency2TextView.text = "1 $selectedCurrency2 = $finalCurrencyValue2 $selectedCurrency1"
            }

            Log.d("currencyValue: ", "$selectedCurrencyValue1 , $selectedCurrencyValue2")
        })

        binding.currency1editText.doOnTextChanged{ text,start,before,count ->
            try {
                var currency1Value = text.toString().toInt()
                val value = String.format("%.4f", currency1Value * finalCurrencyValue1).toDouble()
                binding.currency2editText.setText("${value}")
            }catch (exception:Exception){
//                Toast.makeText(this, "Please Enter Text1!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.currency2editText.doOnTextChanged{ text,start,before,count ->
            try {
                val currency2Value = text.toString().toInt()
                val value = String.format("%.4f", currency2Value * finalCurrencyValue2).toDouble()
                binding.currency1editText.setText("${value}")
            }catch (exception:Exception){
//                Toast.makeText(this, "Please Enter Text2!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.acButtonEditText1.setOnClickListener {
            binding.currency1editText.text = null
            binding.currency2editText.text = null
        }
        binding.acButtonEditText2.setOnClickListener {
            binding.currency1editText.text = null
            binding.currency2editText.text = null
        }

        binding.addButton1.setOnClickListener{
            try {
                binding.currency1editText.setText("${binding.currency1editText.text.toString().toInt()+1}")
            }catch (exception:Exception){
                binding.currency1editText.setText("${binding.currency1editText.text.toString().toDouble()+1}")
            }
        }
        binding.addButton2.setOnClickListener{
            try {
                binding.currency2editText.setText("${binding.currency2editText.text.toString().toInt()+1}")
            }catch (exception:Exception){
                binding.currency2editText.setText("${binding.currency2editText.text.toString().toDouble()+1}")
            }
        }

        binding.minusButton1.setOnClickListener{
            try {
                binding.currency1editText.setText("${binding.currency1editText.text.toString().toInt()-1}")
            }catch (exception:Exception){
                binding.currency1editText.setText("${binding.currency1editText.text.toString().toDouble()-1}")
            }
        }
        binding.minusButton2.setOnClickListener{
            try {
                binding.currency2editText.setText("${binding.currency2editText.text.toString().toInt()-1}")
            }catch (exception:Exception){
                binding.currency2editText.setText("${binding.currency2editText.text.toString().toDouble()-1}")
            }
        }

        binding.refreshButton.setOnClickListener {
            Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show()
        }
    }
}

