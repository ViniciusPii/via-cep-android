package com.example.myapplication.repositories

import com.example.myapplication.api.CepApi
import com.example.myapplication.models.Address
import com.example.myapplication.models.Resource
import retrofit2.HttpException
import java.io.IOException

class CepRepositoryImpl(private val cepApi: CepApi) : CepRepository {
    override suspend fun fetchCepDetails(cep: String): Resource<Address> {
        return try {
            val response = cepApi.fetchCepDetails(cep)
            if (response.isSuccessful) {
                val cepDetails = response.body()
                if (cepDetails?.cep != null) {
                    Resource.Success(cepDetails)
                } else {
                    Resource.Error("CEP não encontrado")
                }
            } else {
                Resource.Error("Erro ao buscar os detalhes do CEP")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Erro de conexão com a API")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("Erro na resposta da API: ${e.code()}")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Erro ao buscar os detalhes do CEP")
        }
    }
}
