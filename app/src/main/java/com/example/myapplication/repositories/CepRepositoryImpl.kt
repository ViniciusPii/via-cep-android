package com.example.myapplication.repositories

import com.example.myapplication.api.CepApi
import com.example.myapplication.models.Cep
import com.example.myapplication.ui.viewmodels.CepViewModel

class CepRepositoryImpl(private val cepApi: CepApi) : CepRepository {
    override suspend fun fetchCepDetails(cep: String): CepViewModel.Resource<Cep> {
        return try {
            val response = cepApi.fetchCepDetails(cep)
            if (response.isSuccessful) {
                val cepDetails = response.body()
                if (cepDetails?.cep != null) {
                    CepViewModel.Resource.Success(cepDetails)
                } else {
                    CepViewModel.Resource.Error("CEP não encontrado")
                }
            } else {
                CepViewModel.Resource.Error("Erro ao buscar os detalhes do CEP")
            }
        } catch (e: Exception) {
            CepViewModel.Resource.Error("Erro ao buscar os detalhes do CEP")
        }
    }
}
