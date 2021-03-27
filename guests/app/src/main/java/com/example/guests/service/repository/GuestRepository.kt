package com.example.guests.service.repository

import android.content.ContentValues
import android.content.Context
import com.example.guests.service.constants.DatabaseConstants
import com.example.guests.service.model.GuestModel

class GuestRepository private constructor(context: Context) {

    private var mGuestDatabaseHelper = GuestDatabaseHelper(context)

    companion object {

        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!Companion::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun save(guest: GuestModel): Boolean {
        return try {
            val db = mGuestDatabaseHelper.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)
            db.insert(DatabaseConstants.GUEST.TABLE_NAME, null, contentValues)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun get(id: Int): GuestModel? {
        var guest: GuestModel? = null
        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val projection = arrayOf(
                DatabaseConstants.GUEST.COLUMNS.NAME,
                DatabaseConstants.GUEST.COLUMNS.ID,
                DatabaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = "${DatabaseConstants.GUEST.COLUMNS.ID} = ?"
            val args = arrayOf(id.toString())
            val cursor = db.query(
                DatabaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )
            if (cursor != null && cursor.count > 0) {
               cursor.moveToFirst()
            }
            guest
        } catch (e: Exception) {
            guest
        }
    }

    fun getAll(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return list
    }

    fun getPresent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return list
    }

    fun getAbsent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return list
    }


    fun update(guest: GuestModel): Boolean {
        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)

            val selection = "${DatabaseConstants.GUEST.COLUMNS.ID} = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DatabaseConstants.GUEST.TABLE_NAME, contentValues, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val selection = "${DatabaseConstants.GUEST.COLUMNS.ID} = ?"
            val args = arrayOf(id.toString())

            db.delete(DatabaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }
}