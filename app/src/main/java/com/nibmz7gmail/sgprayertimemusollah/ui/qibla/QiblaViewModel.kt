package com.nibmz7gmail.sgprayertimemusollah.ui.qibla

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nibmz7gmail.sgprayertimemusollah.core.data.LocationLiveData
import com.nibmz7gmail.sgprayertimemusollah.core.data.qiblafinder.QiblaCompass
import com.nibmz7gmail.sgprayertimemusollah.core.data.qiblafinder.QiblaError
import com.nibmz7gmail.sgprayertimemusollah.core.domain.LoadTodaysDataUseCase
import com.nibmz7gmail.sgprayertimemusollah.core.model.CalendarData
import com.nibmz7gmail.sgprayertimemusollah.core.result.Result
import com.nibmz7gmail.sgprayertimemusollah.core.util.switchMap
import timber.log.Timber
import javax.inject.Inject

class QiblaViewModel @Inject constructor(
	private val qiblaCompass: QiblaCompass,
    locationLiveData: LocationLiveData
) : ViewModel() {

    private val _calendarDataObservable = MediatorLiveData<Result<FloatArray>>()
	val calendarDataObservable: LiveData<Result<FloatArray>>
        get() = _calendarDataObservable

    init {
        _calendarDataObservable.addSource(locationLiveData) {
            if(it == null) {
                _calendarDataObservable.value = Result.Error(QiblaError.NULL)
            }
            else {
                qiblaCompass.start(it)
            }
        }
        _calendarDataObservable.addSource(qiblaCompass) {
            _calendarDataObservable.value = it
        }
    }

}