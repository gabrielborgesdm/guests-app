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
            val db = mGuestDatabaseHelper.readableDatabase

            val projection = arrayOf(
                DatabaseConstants.GUEST.COLUMNS.NAME,
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
                val presence = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)) == 1
                val name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME))
                guest = GuestModel(id, name, presence)
                cursor.close()
            }
            guest
        } catch (e: Exception) {
            guest
        }
    }

    private fun getMultiple(sql: String? = null): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val cursor = db.rawQuery(sql ?: "SELECT * FROM ${DatabaseConstants.GUEST.TABLE_NAME}", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()){
                    val presence = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)) == 1
                    val name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME))
                    val id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID))
                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }
                cursor.close()
            }
            list
        } catch (e: Exception) {
            list
        }
    }


    fun getAll(): List<GuestModel> {
        return getMultiple()
    }

    fun getPresent(): List<GuestModel> {
        return getMultiple("SELECT * FROM ${DatabaseConstants.GUEST.TABLE_NAME} WHERE ${DatabaseConstants.GUEST.COLUMNS.PRESENCE} = 1")
    }

    fun getAbsent(): List<GuestModel> {
        return getMultiple("SELECT * FROM ${DatabaseConstants.GUEST.TABLE_NAME} WHERE ${DatabaseConstants.GUEST.COLUMNS.PRESENCE} = 0")
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