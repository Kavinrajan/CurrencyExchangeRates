package com.task.currexchangerates.data.models

import com.google.gson.annotations.SerializedName

data class IbanResponse (

    @SerializedName("valid"                ) var valid              : Boolean?  = null,
    @SerializedName("message"              ) var message            : String?   = null,
    @SerializedName("iban"                 ) var iban               : String?   = null,
    @SerializedName("country_iban_example" ) var countryIbanExample : String?   = null

)
