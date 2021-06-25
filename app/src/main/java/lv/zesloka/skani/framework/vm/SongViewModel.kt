package lv.zesloka.skani.framework.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import lv.zesloka.core.data.Song
import lv.zesloka.skani.framework.redux.action.SaveSong
import lv.zesloka.skani.framework.redux.appStore as store
import lv.zesloka.skani.framework.redux.state.SongDetailsState
import org.reduxkotlin.StoreSubscription

class SongViewModel(application: Application) : AndroidViewModel(application) {

    private val currentSong: Song
    private val storeSubscription: StoreSubscription
    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val shouldClose = MutableLiveData<Boolean>()

    init {
        storeSubscription = store.subscribe { }
        val state = SongDetailsState.selectFrom(store.state)
        currentSong = state.currentSong
        title.postValue(currentSong.title)
        content.postValue(currentSong.content)
    }

    fun saveSong(title: String, content: String) {
        store.dispatch(SaveSong(currentSong.copy(title = title, content = content)))
    }

    override fun newState(state: SongDetailsState) {
        state.currentSong.let {
            title.postValue(it.title)
            content.postValue(it.content)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
