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
import com.example.myapplication.models.State
import com.example.myapplication.ui.viewmodels.CepViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: CepViewModel by viewModel()

    private val stateObserver = Observer<State<Address>> { state ->
        when (state) {
            is State.Loading -> showLoading()
            is State.Success -> showAddress(state.data)
            is State.Error -> showError(state.message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configureListeners()

        viewModel.address.observe(this, stateObserver)
    }

    private fun showLoading() {
        binding.cardView.visibility = View.GONE
        binding.searchButton.visibility = View.GONE
        binding.cepEditText.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showAddress(cep: Address) {
        binding.cardView.visibility = View.VISIBLE
        binding.searchButton.visibility = View.VISIBLE
        binding.cepEditText.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        binding.cepTextView.text = getString(R.string.cep_label, cep.cep)
        binding.logradouroTextView.text = getString(R.string.street_label, cep.street)
        binding.bairroTextView.text = getString(R.string.neighborhood_label, cep.neighborhood)
        binding.cidadeTextView.text = getString(R.string.city_label, cep.city)
        binding.estadoTextView.text = getString(R.string.state_label, cep.state)
    }

    private fun showError(message: String) {
        binding.searchButton.visibility = View.VISIBLE
        binding.cepEditText.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun configureListeners() {
        binding.searchButton.setOnClickListener {
            hideKeyboard()

            val cep = binding.cepEditText.unMasked
            viewModel.fetchCepDetails(cep)
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

}