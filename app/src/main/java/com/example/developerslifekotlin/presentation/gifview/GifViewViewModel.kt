package com.example.developerslifekotlin.presentation.gifview

import androidx.lifecycle.Transformations
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.developerslifekotlin.data.network.DevelopersLifeApiFilter
import com.example.developerslifekotlin.domain.entity.DomainGif
import com.example.developerslifekotlin.domain.usecases.ClearDatabaseUseCase
import com.example.developerslifekotlin.domain.usecases.GetGifUseCase
import com.example.developerslifekotlin.utils.BaseRxViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

enum class DevelopersLifeApiStatus { LOADING, ERROR, DONE }

class GifViewViewModel @Inject constructor(
    private val getGifUseCase: GetGifUseCase,
    private val clearDatabaseUseCase: ClearDatabaseUseCase,
) : BaseRxViewModel() {

    private var currentFilter: DevelopersLifeApiFilter = DevelopersLifeApiFilter.SHOW_LATEST

    private val numGif = MutableLiveData<Int>()

    private val _status = MutableLiveData<DevelopersLifeApiStatus>()
    val status: LiveData<DevelopersLifeApiStatus>
        get() = _status

    private val _gif = MutableLiveData<DomainGif>()
    val gif: LiveData<DomainGif>
        get() = _gif

    val descriptionVisible = Transformations.map(status) {
        it == DevelopersLifeApiStatus.DONE
    }

    val nextEnabled = Transformations.map(status) {
        it != DevelopersLifeApiStatus.LOADING
    }

    val backEnabled = Transformations.map(numGif) {
        it > 1.toLong()
    }

    init {
        clearDatabase()
    }

    fun onNext() {
        numGif.value = numGif.value?.plus(1)
        numGif.value?.let { getGif(currentFilter, it) }
    }

    fun onPrev() {
        if (status.value == DevelopersLifeApiStatus.DONE) {
            numGif.value = numGif.value?.minus(1)
        }
        numGif.value?.let { getGif(currentFilter, it) }
    }

    private fun getGif(category: DevelopersLifeApiFilter, id: Int) {
        getGifUseCase(category.value, id)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _status.value = DevelopersLifeApiStatus.LOADING }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _gif.value = it
                    _status.value = DevelopersLifeApiStatus.DONE
                },
                onError = {
                    _status.value = DevelopersLifeApiStatus.ERROR
                    numGif.value = numGif.value?.minus(1)
                }
            )
            .disposeOnFinish()
    }

    private fun clearDatabase() {
        clearDatabaseUseCase()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _status.value = DevelopersLifeApiStatus.LOADING }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    _status.value = DevelopersLifeApiStatus.DONE
                    numGif.value = 0
                    onNext()
                },
                onError = {
                    _status.value = DevelopersLifeApiStatus.ERROR
                    numGif.value = 0
                }
            )
            .disposeOnFinish()
    }

    fun chooseCategory(category: Int) {
        when (category) {
            0 -> {
                currentFilter = DevelopersLifeApiFilter.SHOW_LATEST
            }
            1 -> {
                currentFilter = DevelopersLifeApiFilter.SHOW_TOP
            }
            2 -> {
                currentFilter = DevelopersLifeApiFilter.SHOW_HOT
            }
        }
        clearDatabase()
    }
}
