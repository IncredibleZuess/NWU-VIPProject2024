/**
 * @author Carlo Barnardo
 * @editor Sebastian Klopper
 *
 * Contains all the helper functions for the CSV files
 */
package com.example.screentimeapp.helper

import com.csvreader.CsvReader
import com.csvreader.CsvWriter
import com.example.screentimeapp.model.UsageRecord
import java.io.File
import java.io.FileWriter

object CsvHelper {
    /**
     * The header of the CSV file
     */
    val usageHeader = arrayOf("packageName", "startTime", "duration")


    /**
     * Writes the records to the file
     */
    fun write(file: File, records: List<UsageRecord>): Boolean {
        val alreadyExist = File(file.path).exists()
        return try {
            val writer = CsvWriter(FileWriter(file, true), ',')
            if (!alreadyExist) {
                writer.writeRecord(usageHeader)
            }
            for (record in records) {
                writer.writeRecord(record.toArray())
            }
            writer.close()
            true
        } catch (e: Exception) {
            Logger.error("CsvHelper", e.message + ": " + e.localizedMessage)
            false
        }
    }

    /**
     * Reads the records from the file
     */
    fun read(file: File): List<UsageRecord> {
        try {
            if (!file.canRead()) {
                Logger.debug("CsvHelper", "can't read file ${file.path}")
                return listOf()
            }
            val records = arrayListOf<UsageRecord>()
            val reader = CsvReader(file.path)
            reader.readHeaders()
            while (reader.readRecord()) {
                val record = UsageRecord(reader.get(0), reader.get(1).toLong(), reader.get(2).toLong())
                records.add(record)
            }
            reader.close()
            return records
        } catch (e: Exception) {
            Logger.error("CsvHelper", e.message + ": " + e.localizedMessage)
            return listOf()
        }
    }
}