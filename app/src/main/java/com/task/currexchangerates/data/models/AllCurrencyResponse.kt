package com.task.currexchangerates.data.models

import com.google.gson.annotations.SerializedName

data class AllCurrencyResponse (

    @SerializedName("base"      ) var base      : String?  = null,
    @SerializedName("date"      ) var date      : String?  = null,
    @SerializedName("rates"     ) val rates: Map<String, Double>,
    @SerializedName("success"   ) var success   : Boolean? = null,
    @SerializedName("timestamp" ) var timestamp : Int?     = null

)