package ru.test.storeapp.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger
import presentation.di.catalogueRepositoryModule
import presentation.di.catalogueServiceModule
import presentation.di.catalogueUseCaseModule
import presentation.di.mongoModule

fun Application.configureDI() {
    install(Koin) {
        SLF4JLogger()
        modules(
            mongoModule,
            catalogueRepositoryModule,
            catalogueUseCaseModule,
            catalogueServiceModule,
        )
    }
}