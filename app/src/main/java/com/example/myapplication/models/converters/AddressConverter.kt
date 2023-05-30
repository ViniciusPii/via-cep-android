package com.example.myapplication.models.converters

import com.example.myapplication.models.Address
import com.example.myapplication.models.response.AddressResponse

interface AddressConverter {
    fun converter(addressResponse: AddressResponse): Address
}