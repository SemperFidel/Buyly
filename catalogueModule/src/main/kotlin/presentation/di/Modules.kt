package presentation.di

import data.datasource.MongoConnection
import data.datasource.MongoConnectionImpl
import data.repositoryImpl.CatalogueRepositoryImpl
import domain.repository.CatalogueRepository
import domain.useCase.CreateProductUseCase
import domain.useCase.DeleteProductUseCase
import domain.useCase.GetAllProductsUseCase
import domain.useCase.GetProductByIdUseCase
import domain.useCase.UpdateProductUseCase
import org.koin.dsl.module
import presentation.service.CatalogueService

fun mongoModule(url: String, name: String) = module{
    single<MongoConnection> { MongoConnectionImpl(url, name) }
}

val catalogueRepositoryModule = module {
    single<CatalogueRepository> { CatalogueRepositoryImpl(get()) }
}

val catalogueUseCaseModule = module {
    single { GetAllProductsUseCase(get()) }
    single { GetProductByIdUseCase(get()) }
    single { CreateProductUseCase(get()) }
    single { UpdateProductUseCase(get()) }
    single { DeleteProductUseCase(get()) }
}

val catalogueServiceModule = module {
    single { CatalogueService(
        get(), get(), get(),
        get(), get()
    ) }
}