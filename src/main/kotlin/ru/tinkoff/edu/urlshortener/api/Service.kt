package ru.tinkoff.edu.urlshortener.api

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import ru.tinkoff.edu.urlshortener.UrlGenerator
import java.net.HttpURLConnection
import java.net.URL

@Service
class Service(
    private val dao: Dao,
    private val urlGenerator: UrlGenerator
) {
    init {
        urlGenerator.generatedUrlLength = dao.getGeneratedUrlLength()
        urlGenerator.counter = dao.getUrlGeneratorCounter()
    }

    fun find(shortUrl: String) = dao.find(shortUrl)

    fun add(forwardUrl: String): String {
        try {
            isUrlReachable(forwardUrl)
        } catch (e: Exception) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST, e::class.java.name + " " + e.message
            )
        }

        var shortUrl = dao.findByForwardUrl(forwardUrl)
        if (shortUrl != null) return shortUrl

        shortUrl = urlGenerator.next()
        dao.setUrlGeneratorProperties(urlGenerator.counter, urlGenerator.generatedUrlLength)

        dao.add(shortUrl, forwardUrl)
        return shortUrl
    }

    private fun isUrlReachable(forwardUrl: String) {
        val url = URL(forwardUrl) // Detects malformed URLs
        val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        httpURLConnection.instanceFollowRedirects = false
        httpURLConnection.responseCode // Checks URL availability
    }
}
