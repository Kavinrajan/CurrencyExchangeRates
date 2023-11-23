package com.task.currexchangerates.data.models


import com.google.gson.annotations.SerializedName

data class CurrencyResponse (
    @SerializedName("success" ) var success : Boolean? = null,
    @SerializedName("query"   ) var query   : Query?   = Query(),
    @SerializedName("info"    ) var info    : Info?    = Info(),
    @SerializedName("date"    ) var date    : String?  = null,
    @SerializedName("result"  ) var result  : Double?  = null
)

data class Query (
    @SerializedName("from"   ) var from   : String? = null,
    @SerializedName("to"     ) var to     : String? = null,
    @SerializedName("amount" ) var amount : Int?    = null
)

data class Info (
    @SerializedName("timestamp" ) var timestamp : Int?    = null,
    @SerializedName("rate"      ) var rate      : Double? = null
)