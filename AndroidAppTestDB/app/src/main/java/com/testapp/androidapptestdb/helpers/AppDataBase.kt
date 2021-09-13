package com.testapp.androidapptestdb.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.google.gson.Gson
import com.testapp.androidapptestdb.models.GroceryList
import android.database.DatabaseUtils


class AppDataBase(private val context: Context) :
    SQLiteOpenHelper(
        context,
        DataBaseConstants.DATA_BASE_NAME,
        null,
        DataBaseConstants.DATA_BASE_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DataBaseQueries.CREATE_GROCERY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DataBaseQueries.DROP_GROCERY_TABLE)
        onCreate(db)
    }

    fun addGroceryList(groceryList: GroceryList): Boolean {
        return try {
            val columnParams = ContentValues()
            columnParams.apply {
                put(DataBaseConstants.TABLE_GROCERY_NAME, groceryList.name)
                put(DataBaseConstants.TABLE_GROCERY_ITEMS, groceryList.items)
                put(DataBaseConstants.TABLE_GROCERY_STATUS, groceryList.status)
                put(DataBaseConstants.TABLE_GROCERY_CREATED_TIME, groceryList.createdAt)
                put(DataBaseConstants.TABLE_GROCERY_UPDATED_TIME, groceryList.updatedAt)
            }
            writableDatabase.insert(DataBaseConstants.TABLE_GROCERY, null, columnParams)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getAllGroceryLists(): ArrayList<GroceryList> {
        val groceryLists = ArrayList<GroceryList>()
        try {
            val cursor: Cursor =
                readableDatabase.rawQuery(DataBaseQueries.GET_ALL_GROCERY_LIST, null)
            cursor.moveToFirst()
            while (cursor.position < cursor.count) {

                val listId =
                    cursor.getIntOrNull(cursor.getColumnIndex(DataBaseConstants.TABLE_GROCERY_ID))
                        ?: 0
                val listName =
                    cursor.getStringOrNull(cursor.getColumnIndex(DataBaseConstants.TABLE_GROCERY_NAME))
                        ?: ""
                val listStatus =
                    cursor.getStringOrNull(cursor.getColumnIndex(DataBaseConstants.TABLE_GROCERY_STATUS))
                        ?: ""
                val listCreatedAt =
                    cursor.getStringOrNull(cursor.getColumnIndex(DataBaseConstants.TABLE_GROCERY_CREATED_TIME))
                        ?: ""
                val listIUpdatedAt =
                    cursor.getStringOrNull(cursor.getColumnIndex(DataBaseConstants.TABLE_GROCERY_UPDATED_TIME))
                        ?: ""
                val listItems =
                    cursor.getStringOrNull(cursor.getColumnIndex(DataBaseConstants.TABLE_GROCERY_ITEMS))
                        ?: ""
                groceryLists.add(GroceryList().apply {
                    id = listId
                    name = listName
                    status = listStatus
                    createdAt = listCreatedAt
                    updatedAt = listIUpdatedAt
                    items = listItems
                    initItemsFromJson()
                })
                cursor.moveToNext()
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return groceryLists
    }

    fun getGroceryListsByStatus(filterStatus: String): ArrayList<GroceryList> {
        val groceryLists = ArrayList<GroceryList>()
        val cursor: Cursor =
            readableDatabase.rawQuery(
                DataBaseQueries.GET_STATUS_GROCERY_LIST + "'$filterStatus'",
                null
            )
        cursor.moveToFirst()
        while (cursor.position < cursor.count) {

            val listId =
                cursor.getIntOrNull(cursor.getColumnIndex(DataBaseConstants.TABLE_GROCERY_ID)) ?: 0
            val listName =
                cursor.getStringOrNull(cursor.getColumnIndex(DataBaseConstants.TABLE_GROCERY_NAME))
                    ?: ""
            val listStatus =
                cursor.getStringOrNull(cursor.getColumnIndex(DataBaseConstants.TABLE_GROCERY_STATUS))
                    ?: ""
            val listCreatedAt =
                cursor.getStringOrNull(cursor.getColumnIndex(DataBaseConstants.TABLE_GROCERY_CREATED_TIME))
                    ?: ""
            val listIUpdatedAt =
                cursor.getStringOrNull(cursor.getColumnIndex(DataBaseConstants.TABLE_GROCERY_UPDATED_TIME))
                    ?: ""
            val listItems =
                cursor.getStringOrNull(cursor.getColumnIndex(DataBaseConstants.TABLE_GROCERY_ITEMS))
                    ?: ""
            groceryLists.add(GroceryList().apply {
                id = listId
                name = listName
                status = listStatus
                createdAt = listCreatedAt
                updatedAt = listIUpdatedAt
                items = listItems
                initItemsFromJson()
            })
            cursor.moveToNext()
        }
        cursor.close()
        return groceryLists
    }

    fun updateGroceryList(groceryList: GroceryList): Boolean {
        return try {
            val columnParams = ContentValues()
            columnParams.apply {
                put(DataBaseConstants.TABLE_GROCERY_NAME, groceryList.name)
                put(DataBaseConstants.TABLE_GROCERY_ITEMS, groceryList.items)
                put(DataBaseConstants.TABLE_GROCERY_STATUS, groceryList.status)
                put(DataBaseConstants.TABLE_GROCERY_CREATED_TIME, groceryList.createdAt)
                put(DataBaseConstants.TABLE_GROCERY_UPDATED_TIME, groceryList.updatedAt)
            }
            writableDatabase.update(
                DataBaseConstants.TABLE_GROCERY,
                columnParams,
                "${DataBaseConstants.TABLE_GROCERY_ID} = " + groceryList.id.toString(),
                null
            )
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun updateGroceryListStatus(
        groceryListId: String,
        status: String
    ): Boolean {
        return try {
            val columnParams = ContentValues()
            columnParams.apply {
                put(DataBaseConstants.TABLE_GROCERY_STATUS, status)
                put(
                    DataBaseConstants.TABLE_GROCERY_UPDATED_TIME,
                    System.currentTimeMillis().toString()
                )
            }
            writableDatabase.update(
                DataBaseConstants.TABLE_GROCERY,
                columnParams,
                "${DataBaseConstants.TABLE_GROCERY_ID} = " + groceryListId,
                null
            )
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun deleteGroceryList(listId: Int): Int {
        return try {
            writableDatabase.delete(
                DataBaseConstants.TABLE_GROCERY,
                "${DataBaseConstants.TABLE_GROCERY_ID} = ", arrayOf(listId.toString())
            )
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }

    }

    fun getNumberOfGroceryLists(): Int {
        return try {
            DatabaseUtils.queryNumEntries(readableDatabase, DataBaseConstants.TABLE_GROCERY).toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }
}