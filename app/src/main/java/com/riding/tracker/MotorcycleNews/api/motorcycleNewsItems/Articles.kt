package com.riding.tracker.motorcyclenews.api.motorcycleNewsItems

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class Articles (
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