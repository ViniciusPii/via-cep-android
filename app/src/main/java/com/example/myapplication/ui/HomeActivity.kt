package com.example.myapplication.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.models.Cep
import com.example.myapplication.ui.viewmodels.CepViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: CepViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            val cep = binding.cepEditText.text.toString()
            hideKeyboard()
            viewModel.fetchCepDetails(cep)
        }

        viewModel.cepDetails.observe(this) { resource ->
            when (resource) {
                is CepViewModel.Resource.Loading -> showLoading()
                is CepViewModel.Resource.Success -> showCepDetails(resource.data)
                is CepViewModel.Resource.Error -> showError(resource.message)
            }
        }
    }

    private fun showLoading() {
        binding.cardView.visibility = View.GONE
        binding.searchButton.visibility = View.GONE
        binding.cepEditText.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showCepDetails(cep: Cep) {
        binding.cardView.visibility = View.VISIBLE
        binding.searchButton.visibility = View.VISIBLE
        binding.cepEditText.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        binding.cepTextView.text = cep.cep
        binding.logradouroTextView.text = cep.logradouro
        binding.bairroTextView.text = cep.bairro
        binding.cidadeTextView.text = cep.localidade
        binding.estadoTextView.text = cep.uf
    }

    private fun showError(message: String) {
        binding.searchButton.visibility = View.VISIBLE
        binding.cepEditText.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

}