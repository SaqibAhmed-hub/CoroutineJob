package com.example.coroutinejob

data class employee(var data: data,var support: support)

class data(
    var id:Int,
    var email: String,
    var first_name : String,
    var last_name : String,
    var avatar : String
){}

class support(
    var url: String,
    var text: String
){}