package com.example.guests.service.constants

class DatabaseConstants private constructor(){

    object GUEST {
        const val TABLE_NAME = "Guest"

        object COLUMNS  {
            const val ID = "id"
            const val NAME = "name"
            const val PRESENCE = "presence"
        }
    }
}