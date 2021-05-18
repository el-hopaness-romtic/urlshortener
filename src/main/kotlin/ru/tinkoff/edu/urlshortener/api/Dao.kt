package ru.tinkoff.edu.urlshortener.api

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import ru.tinkoff.edu.urlshortener.model.UrlGeneratorProperties
import ru.tinkoff.edu.urlshortener.model.UrlMap
import javax.sql.DataSource

@Repository
class Dao(
    dataSource: DataSource
) {
    init {
        Database.connect(dataSource)
    }

    fun find(shortUrl: String): String? = transaction {
        UrlMap.select { UrlMap.shortUrl eq shortUrl }.singleOrNull()?.get(UrlMap.forwardUrl)
    }

    fun add(shortUrl: String, forwardUrl: String): Unit = transaction {
        UrlMap.insert {
            it[UrlMap.shortUrl] = shortUrl
            it[UrlMap.forwardUrl] = forwardUrl
        }
    }

    fun findByForwardUrl(forwardUrl: String): String? = transaction {
        UrlMap.select { UrlMap.forwardUrl eq forwardUrl }.singleOrNull()?.get(UrlMap.shortUrl)
    }

    fun getUrlGeneratorCounter() = transaction {
        UrlGeneratorProperties.selectAll().single()[UrlGeneratorProperties.counter]
    }

    fun setUrlGeneratorCounter(counter: Long) = transaction {
        UrlGeneratorProperties.update {
            it[UrlGeneratorProperties.counter] = counter
        }
    }

    fun getGeneratedUrlLength() = transaction {
        UrlGeneratorProperties.selectAll().single()[UrlGeneratorProperties.generatedUrlLength]
    }

    fun setGeneratedUrlLength(generatedUrlLength: Int) = transaction {
        UrlGeneratorProperties.update {
            it[UrlGeneratorProperties.generatedUrlLength] = generatedUrlLength
        }
    }
}
