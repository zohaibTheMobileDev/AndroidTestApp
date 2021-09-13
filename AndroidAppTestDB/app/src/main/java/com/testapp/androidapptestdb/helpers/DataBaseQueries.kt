package com.testapp.androidapptestdb.helpers

object DataBaseQueries {

    const val CREATE_GROCERY_TABLE =
        "create table ${DataBaseConstants.TABLE_GROCERY} (${DataBaseConstants.TABLE_GROCERY_ID} integer primary key, ${DataBaseConstants.TABLE_GROCERY_NAME} text,${DataBaseConstants.TABLE_GROCERY_ITEMS} text,${DataBaseConstants.TABLE_GROCERY_STATUS} text, ${DataBaseConstants.TABLE_GROCERY_CREATED_TIME} text,${DataBaseConstants.TABLE_GROCERY_UPDATED_TIME} text)"
    const val DROP_GROCERY_TABLE = "DROP TABLE IF EXISTS ${DataBaseConstants.TABLE_GROCERY}"
    const val GET_ALL_GROCERY_LIST = "SELECT * FROM ${DataBaseConstants.TABLE_GROCERY}"
    const val GET_STATUS_GROCERY_LIST =
        "SELECT * FROM ${DataBaseConstants.TABLE_GROCERY} WHERE ${DataBaseConstants.TABLE_GROCERY_STATUS} = "

}