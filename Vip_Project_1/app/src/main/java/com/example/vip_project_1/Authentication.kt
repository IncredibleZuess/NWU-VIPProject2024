/**
 * @author Carlo Barnardo
 * @editor Sebastian Klopper
 */
package com.example.vip_project_1

class Authentication {
    /**
     * Validates that the user is the admin user -> future versions will contain a user database
     */
    fun validateCredentials(username: String, password: String): Boolean {
        return username == "admin" && password == "000000"
    }
}