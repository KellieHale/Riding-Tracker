package com.riding.tracker.motorcycleNews.api

import com.riding.tracker.roomdb.motorcycleNews.Articles
import com.squareup.moshi.Json
import org.json.JSONObject
import java.net.URL
import kotlin.Unit.toString


fun parseMotorcycleNewsJsonResult(jsonResult: JSONObject): Articles {
    val title = jsonResult.getString("title")
    val description = jsonResult.getString("description")
return Articles(
    title = title,
    description = description
    )
}