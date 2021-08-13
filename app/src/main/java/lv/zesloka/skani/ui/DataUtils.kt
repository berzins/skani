package lv.zesloka.skani.ui

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.postIfNew(next: T) {
    if (this.value != next) {
        postValue(next)
    }
}