package com.example.book_flight_mobile.di

import com.example.book_flight_mobile.api.ApiService
import com.example.book_flight_mobile.repositories.AirportRepository
import com.example.book_flight_mobile.repositories.FlightRepository
import com.example.book_flight_mobile.repositories.MainLog
import com.example.book_flight_mobile.repositories.TicketRepository
import com.example.book_flight_mobile.repositories.UserRepository
import com.example.book_flight_mobile.repositories.impl.AirportRepositoryImpl
import com.example.book_flight_mobile.repositories.impl.FlightRepositoryImpl
import com.example.book_flight_mobile.repositories.impl.MainLogImpl
import com.example.book_flight_mobile.repositories.impl.TicketRepositoryImpl
import com.example.book_flight_mobile.repositories.impl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    @Singleton
    abstract fun bindMainLog(log: MainLogImpl): MainLog

    @Binds
    @Singleton
    abstract fun bindAirportRepository(airportRepository: AirportRepositoryImpl): AirportRepository

    @Binds
    @Singleton
    abstract fun bindFlightRepository(flightRepository: FlightRepositoryImpl): FlightRepository

    @Binds
    @Singleton
    abstract fun bindTicketRepository(ticketRepository: TicketRepositoryImpl): TicketRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository
}
