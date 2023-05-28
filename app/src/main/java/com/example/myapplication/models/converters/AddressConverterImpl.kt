package com.example.myapplication.models.converters

import com.example.myapplication.models.Address
import com.example.myapplication.models.response.AddressResponse

class AddressConverterImpl: AddressConverter {
    override fun converter(addressResponse: AddressResponse): Address {
        return Address(
            cep = addressResponse.cep,
            street = addressResponse.logradouro,
            neighborhood = addressResponse.bairro,
            city = addressResponse.localidade,
            state = addressResponse.uf
        )
    }
}