package com.app.majuapp.domain.usecase

import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.domain.model.walk.WalkDateHistoryDomainModel
import com.app.majuapp.domain.repository.RecordCalendarRepository
import com.app.majuapp.util.NetworkResult
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class RecordCalendarUsecase @Inject constructor(
    private val recordCalendarRepository: RecordCalendarRepository
){


    val monthEvents: StateFlow<Map<String, BooleanArray>> = recordCalendarRepository.monthEvents
    val walkingHistoryMonthEvents: StateFlow<Map<String, Boolean>> = recordCalendarRepository.walkingHistoryMonthEvents
    val walkingHistoryDateEvents: StateFlow<List<WalkDateHistoryDomainModel>> = recordCalendarRepository.walkingHistoryDateEvents
    val cultureLikeMonthEvents: StateFlow<Map<String, Boolean>> = recordCalendarRepository.cultureLikeMonthEvents
    val cultureLikeDateEvents: StateFlow<List<CultureEventDomainModel>> = recordCalendarRepository.cultureLikeDateEvents
    val cultureLikeMonthEventsNetworkResult: StateFlow<NetworkResult<NetworkDto<Map<String, Boolean>>>> = recordCalendarRepository.cultureLikeMonthEventsNetworkResult
    val cultureLikeDateEventsNetworkResult: StateFlow<NetworkResult<NetworkDto<List<CultureEventDomainModel>>>> = recordCalendarRepository.cultureLikeDateEventsNetworkResult
    val walkingHistoryMonthEventsNetworkResult: StateFlow<NetworkResult<NetworkDto<Map<String, Boolean>>>> = recordCalendarRepository.walkingHistoryMonthEventsNetworkResult
    val walkingHistoryDateEventsNetworkResult: StateFlow<NetworkResult<NetworkDto<List<WalkDateHistoryDomainModel>>>> = recordCalendarRepository.walkingHistoryDateEventsNetworkResult

    suspend fun getMonthEvents(date: String) = recordCalendarRepository.getMonthEvents(date)
    suspend fun getCultureLikeMonthEvents(date: String) = recordCalendarRepository.getCultureLikeMonthEvents(date)
    suspend fun getCultureLikeDateEvents(date: String) = recordCalendarRepository.getCultureLikeDateEvents(date)
    suspend fun getWalkingHistoryMonthEvents(date: String) = recordCalendarRepository.getWalkingHistoryMonthEvents(date)
    suspend fun getWalkingHistoryDateEvents(date: String) = recordCalendarRepository.getWalkingHistoryDateEvents(date)
}