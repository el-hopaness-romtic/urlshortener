package ru.tinkoff.edu.urlshortener.model

import org.jetbrains.exposed.sql.Table

object UrlMap : Table("url_map") {
    val shortUrl = char("short_url", 5)
    val forwardUrl = text("forward_url")
}
