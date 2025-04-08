package com.nikolaa.faktorzweitask.models

data class AddressModel(
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipcode: String?,
    val geo: GeoModel
)