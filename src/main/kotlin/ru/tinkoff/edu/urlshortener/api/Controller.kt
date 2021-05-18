package ru.tinkoff.edu.urlshortener.api

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import springfox.documentation.annotations.ApiIgnore
import javax.servlet.http.HttpServletResponse

@RestController
class Controller(
    private val service: Service
) {
    @ApiIgnore
    @GetMapping("/")
    fun redirectToSwagger(httpServletResponse: HttpServletResponse) {
        httpServletResponse.setHeader("Location", "swagger-ui/")
        httpServletResponse.status = 302
    }

    @ApiOperation("Redirect using short URL")
    @ApiResponses(
        ApiResponse(code = 302, message = "Found"),
        ApiResponse(code = 404, message = "Not Found")
    )
    @GetMapping("/{shortUrl:^[0-9a-zA-Z]{1,10}\$}")
    fun redirect(
        @ApiParam("Short URL") @PathVariable shortUrl: String,
        // httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ) {
        val redirectTo = service.find(shortUrl)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND, "Entity not found"
            )

        httpServletResponse.status = 302
        httpServletResponse.setHeader("Location", redirectTo)
    }

    @ApiOperation("Get short url")
    @ApiResponse(code = 200, message = "OK", response = String::class)
    @PostMapping
    fun add(
        @ApiParam("URL to shorten", example = "https://stackoverflow.com/") @RequestBody forwardUrl: String
    ) = service.add(forwardUrl)
}
