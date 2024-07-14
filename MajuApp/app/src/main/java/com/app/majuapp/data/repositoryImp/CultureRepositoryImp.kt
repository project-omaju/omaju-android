package com.app.majuapp.data.repositoryImp

import android.util.Log
import com.app.majuapp.Application
import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.api.CultureApi
import com.app.majuapp.domain.model.CultureDetailDomainModel
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.domain.repository.CultureRepository
import com.app.majuapp.util.NetworkResult
import com.app.majuapp.util.setNetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CultureRepositoryImp @Inject constructor(
    private val cultureApi: CultureApi
) : CultureRepository { // End of CultureRepositoryImp

    private val _cultureEventList = MutableStateFlow<List<CultureEventDomainModel>>(listOf())
    override val cultureEventList: StateFlow<List<CultureEventDomainModel>> = _cultureEventList

    private val _cultureEventListNetworkResult = MutableStateFlow<NetworkResult<NetworkDto<List<CultureEventDomainModel>>>>(NetworkResult.Idle())
    override val cultureEventListNetworkResult: StateFlow<NetworkResult<NetworkDto<List<CultureEventDomainModel>>>> = _cultureEventListNetworkResult

    private val _cultureEventDetail = MutableStateFlow<NetworkResult<NetworkDto<CultureDetailDomainModel>>>(NetworkResult.Idle())
    override val cultureEventDetail: StateFlow<NetworkResult<NetworkDto<CultureDetailDomainModel>>> = _cultureEventDetail

    private val _cultureEventToggleNetworkResult = MutableStateFlow<NetworkResult<NetworkDto<Boolean>>>(NetworkResult.Idle())
    override val cultureEventToggleNetworkResult: StateFlow<NetworkResult<NetworkDto<Boolean>>> = _cultureEventToggleNetworkResult

    override suspend fun getAllCultureEvents() {
        val response = cultureApi.getAllCultureEvents()
        _cultureEventListNetworkResult.setNetworkResult(response) {
            response.body()?.let {
                when(it.status) {
                    200 -> {
                        _cultureEventList.value = it.data!!
                    }
                    else -> {

                    }
                }
            }
        }
    }

    override suspend fun getGenreCultureEvents(genre: String) {
        val response = cultureApi.getGenreCultureEvents(genre)
        _cultureEventListNetworkResult.setNetworkResult(response){
            response.body()?.let {
                when(it.status) {
                    200 -> {
                        _cultureEventList.value = it.data!!
                    }
                    else -> {

                    }
                }
            }
        }
    }

    override suspend fun getCultureEventsDetail(eventId: Int) {
        val response = cultureApi.getCultureEventsDetail(eventId)
        _cultureEventDetail.setNetworkResult(response)
    }

    override suspend fun toggleCultureLike(eventId: Int) {
        val response = cultureApi.toggleCultureLike(eventId)
        _cultureEventToggleNetworkResult.setNetworkResult(response) {
            response.body()?.let { networkResult ->
                when(networkResult.status) {
                    201 -> {
                        _cultureEventList.value = _cultureEventList.value.map {
                            if (it.id == eventId) {
                                it.copy(likeStatus = networkResult.message!!.toBoolean())
                            }
                            else it
                        }
                    }
                    else -> {

                    }
                }
            }
        }
    }
}