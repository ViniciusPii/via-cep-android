package com.example.myapplication.repositories

import com.example.myapplication.api.CepApi
import com.example.myapplication.models.Address
import com.example.myapplication.models.converters.AddressConverter
import com.example.myapplication.models.response.AddressResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class AddressRepositoryImpl(
    private val cepApi: CepApi,
    private val addressConverter: AddressConverter
) : AddressRepository {
    override suspend fun fetchAddress(cep: String): Address {
        try {
            val response: Response<AddressResponse> = cepApi.fetchAddress(cep)
            if (response.isSuccessful) {
                val addressResponse: AddressResponse? = response.body()
                if (addressResponse?.cep != null) {
                    return addressConverter.converter(addressResponse)
                } else {
                    throw Exception("CEP não encontrado")
                }
            } else {
                throw Exception("Erro ao buscar os detalhes do CEP")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            throw Exception("Erro de conexão com a API")
        } catch (e: HttpException) {
            e.printStackTrace()
            throw Exception("Erro na resposta da API: ${e.code()}")
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Erro ao buscar os detalhes do CEP")
        }
    }
}
