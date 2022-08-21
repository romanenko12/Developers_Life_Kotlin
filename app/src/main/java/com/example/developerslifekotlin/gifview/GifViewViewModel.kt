package com.example.developerslifekotlin.gifview

import androidx.lifecycle.Transformations
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.developerslifekotlin.data.database.DatabaseGif
import com.example.developerslifekotlin.data.network.DevelopersLifeApiFilter
import com.example.developerslifekotlin.data.repository.GifRepository
import javax.inject.Inject

enum class DevelopersLifeApiStatus { LOADING, ERROR, DONE }

class GifViewViewModel @Inject constructor(
    private val repository: GifRepository
    ) : ViewModel() {

    private val maxPage = 2000

    private var currentFilter: DevelopersLifeApiFilter = DevelopersLifeApiFilter.SHOW_LATEST

    private val numGif = MutableLiveData<Long>()

    private val _status = MutableLiveData<DevelopersLifeApiStatus>()
    val status: LiveData<DevelopersLifeApiStatus>
        get() = _status

    private val _gif = MutableLiveData<DatabaseGif>()
    val gif: LiveData<DatabaseGif>
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
        init()
    }

    private fun randomNumber() = (0..maxPage).random()

    private fun init() {
        viewModelScope.launch {
            repository.clearDatabase()
            numGif.value = 0
            getGifFromServer(currentFilter, randomNumber())
        }
    }

    fun onNext() {
        viewModelScope.launch {
            if (numGif.value!! == repository.getCount())
                getGifFromServer(currentFilter, randomNumber())
            else {
                numGif.value = numGif.value?.plus(1)
                getGifFromDatabase(numGif.value!!)
            }
        }
    }

    fun onPrev() {
        if(status.value == DevelopersLifeApiStatus.DONE)
            numGif.value = numGif.value?.minus(1)
        getGifFromDatabase(numGif.value!!)
    }

    private fun getGifFromDatabase(id: Long) {
        viewModelScope.launch {
            _status.value = DevelopersLifeApiStatus.LOADING
            try {
                _gif.value = repository.getGif(id)
                _status.value = DevelopersLifeApiStatus.DONE
            } catch (e: Exception) {
                _status.value = DevelopersLifeApiStatus.ERROR
            }
        }
    }

    private fun getGifFromServer(category: DevelopersLifeApiFilter, number: Int) {
        viewModelScope.launch {
            _status.value = DevelopersLifeApiStatus.LOADING
            try {
                _gif.value = repository.downloadGif(category, number)
                _status.value = DevelopersLifeApiStatus.DONE
                numGif.value = numGif.value?.plus(1)
            } catch (e: Exception) {
                _status.value = DevelopersLifeApiStatus.ERROR
            }
        }
    }

    fun chooseCategory(category: Int){
        when(category){
            0 -> { currentFilter = DevelopersLifeApiFilter.SHOW_LATEST }
            1 -> { currentFilter = DevelopersLifeApiFilter.SHOW_TOP }
            2 -> { currentFilter = DevelopersLifeApiFilter.SHOW_HOT }
        }
        init()
    }
}
