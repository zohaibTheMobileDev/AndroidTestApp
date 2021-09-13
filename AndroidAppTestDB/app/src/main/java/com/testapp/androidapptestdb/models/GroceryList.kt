package com.testapp.androidapptestdb.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class GroceryList : Serializable {

    // Params Saved in Database
    var id: Int = 0
    var name: String = ""
    var items: String = ""
    var status: String = ""
    var createdAt: String = ""
    var updatedAt: String = ""

    // Other Params for computations
    var groceryItemsList = ArrayList<GroceryItem>()

    fun initItemsFromJson() {
        try {
            val type = object : TypeToken<ArrayList<GroceryItem>>() {}.type
//            items = items.replace("\"\\[","[")
//            items = items.replace("\\]\"","[")
            groceryItemsList.addAll(Gson().fromJson(items, type))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}