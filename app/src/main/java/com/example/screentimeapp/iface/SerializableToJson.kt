/**
 * @author Carlo Barnardo
 * @editor Sebastian Klopper
 *
 * Interface for the serialisation of User Data
 */
package com.example.screentimeapp.iface

import org.json.JSONException
import org.json.JSONObject

interface SerializableToJson {
    @Throws(JSONException::class)
    fun toJson(): JSONObject
}