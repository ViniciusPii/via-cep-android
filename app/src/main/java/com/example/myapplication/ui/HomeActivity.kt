package com.example.myapplication.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.models.Address
import com.example.myapplication.models.Resource
import com.example.myapplication.ui.viewmodels.CepViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: CepViewModel by viewModel()

    private val cepObserver = Observer<Resource<Address>> { resource ->
        when (resource) {
            is Resource.Loading -> showLoading()
            is Resource.Success -> showCepDetails(resource.data)
            is Resource.Error -> showError(resource.message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configureListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.cepDetails.observe(this, cepObserver)
    }

    override fun onPause() {
        super.onPause()
        viewModel.cepDetails.removeObserver(cepObserver)
    }

    private fun showLoading() {
        binding.cardView.visibility = View.GONE
        binding.searchButton.visibility = View.GONE
        binding.cepEditText.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showCepDetails(cep: Address) {
        binding.cardView.visibility = View.VISIBLE
        binding.searchButton.visibility = View.VISIBLE
        binding.cepEditText.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        binding.cepTextView.text = getString(R.string.cep_label, cep.cep)
        binding.logradouroTextView.text =
            getString(R.string.logradouro_label, cep.street)
        binding.bairroTextView.text = getString(R.string.bairro_label, cep.neighborhood)
        binding.cidadeTextView.text = getString(R.string.cidade_label, cep.city)
        binding.estadoTextView.text = getString(R.string.estado_label, cep.state)
    }

    private fun showError(message: String) {
        binding.searchButton.visibility = View.VISIBLE
        binding.cepEditText.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun configureListeners() {
        binding.searchButton.setOnClickListener {
            val cep = binding.cepEditText.text.toString()
            hideKeyboard()
            viewModel.fetchCepDetails(cep)
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

}