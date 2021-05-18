package ru.tinkoff.edu.urlshortener.model

import org.jetbrains.exposed.sql.Table

object UrlGeneratorProperties : Table("url_generator_properties") {
    val counter = long("counter")
    val generatedUrlLength = integer("generated_url_length")
}
