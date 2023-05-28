package com.example.myapplication.repositories

import com.example.myapplication.api.CepApi
import com.example.myapplication.models.Address
import com.example.myapplication.models.State
import com.example.myapplication.models.converters.AddressConverter
import com.example.myapplication.models.response.AddressResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class CepRepositoryImpl(
    private val cepApi: CepApi,
    private val addressConverter: AddressConverter
) : CepRepository {
    override suspend fun fetchCepDetails(cep: String): State<Address> {
        return try {
            val response: Response<AddressResponse> = cepApi.fetchCepDetails(cep)
            if (response.isSuccessful) {
                val addressResponse: AddressResponse? = response.body()
                if (addressResponse?.cep != null) {
                    val address: Address = addressConverter.converter(addressResponse)
                    State.Success(address)
                } else {
                    State.Error("CEP não encontrado")
                }
            } else {
                State.Error("Erro ao buscar os detalhes do CEP")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            State.Error("Erro de conexão com a API")
        } catch (e: HttpException) {
            e.printStackTrace()
            State.Error("Erro na resposta da API: ${e.code()}")
        } catch (e: Exception) {
            e.printStackTrace()
            State.Error("Erro ao buscar os detalhes do CEP")
        }
    }
}
