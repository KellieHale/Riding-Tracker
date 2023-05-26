package com.riding.tracker.roomdb.motorcycleNews

import androidx.room.Entity
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.net.URL

@Root(name = "item", strict = false)
data class Articles @JvmOverloads constructor(
    @field:Element(name = "title")
    @param:Element(name = "title")
    var title: String? = null,
    @field:Element(name = "link")
    @param:Element(name = "link")
    var link: String? = null,
    @field:Element(name = "description")
    @param:Element(name = "description")
    var description: String? = null
)