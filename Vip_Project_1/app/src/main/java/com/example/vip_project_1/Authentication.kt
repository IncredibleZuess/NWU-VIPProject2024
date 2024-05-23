package com.example.vip_project_1

class Authentication {
    fun validateCredentials(username: String, password: String): Boolean {
        return username == "admin" && password == "000000"
    }
}