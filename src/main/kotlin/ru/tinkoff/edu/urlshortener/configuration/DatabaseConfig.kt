package ru.tinkoff.edu.urlshortener.configuration

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import javax.sql.DataSource

@Configuration
class DatabaseConfig(
    @Value("\${spring.datasource.url}") val jdbcUrl: String,
    @Value("\${spring.datasource.username}") val username: String,
    @Value("\${spring.datasource.password}") val password: String
) {
    @Bean("flyway")
    fun flyway(): Flyway {
        val fluentConfiguration = Flyway.configure().dataSource(
            jdbcUrl,
            username,
            password
        )

        val flyway = fluentConfiguration.load()
        flyway.migrate()
        return flyway
    }

    @DependsOn("flyway")
    @Bean
    fun dataSource(): DataSource = with(DataSourceBuilder.create()) {
        url(jdbcUrl)
        username(username)
        password(password)
        build()
    }
}
