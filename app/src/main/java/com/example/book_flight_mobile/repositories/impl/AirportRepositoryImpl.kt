package com.example.book_flight_mobile.repositories.impl

import com.example.book_flight_mobile.api.RetrofitInstance
import com.example.book_flight_mobile.models.AirportResponse
import com.example.book_flight_mobile.models.HomeResponse
import com.example.book_flight_mobile.repositories.AirportRepository
import com.example.book_flight_mobile.repositories.MainLog
import javax.inject.Inject



class AirportRepositoryImpl @Inject constructor(
    private val log: MainLog?
) : AirportRepository {

    override suspend fun getHomeSearch(): HomeResponse? {
        return try {
            val homeSearch = RetrofitInstance.api.loadHomeSearch()
            log?.d("AirportRepository", "Retrieved  home search ${homeSearch.airportResponses}")
            homeSearch
        } catch (e: Exception) {
            log?.e("AirportRepository", "Error loading airports: ${e.message}")
            null
        }
    }
}

