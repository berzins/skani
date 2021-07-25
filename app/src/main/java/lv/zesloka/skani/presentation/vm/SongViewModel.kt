package lv.zesloka.skani.presentation.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import lv.zesloka.skani.presentation.redux.action.SaveSong
import lv.zesloka.skani.presentation.redux.appStore as store
import lv.zesloka.skani.presentation.redux.state.song.RdxSongDetailsState
import lv.zesloka.domain.model.Song
import lv.zesloka.skani.presentation.redux.state.extentions.from
import lv.zesloka.skani.presentation.redux.state.extentions.selectFrom
import lv.zesloka.skani.presentation.redux.state.extentions.toSong
import lv.zesloka.skani.presentation.redux.state.song.RdxSong
import org.reduxkotlin.StoreSubscription

class SongViewModel(application: Application) : AndroidViewModel(application) {

    private val currentSong: Song
    private val storeSubscription: StoreSubscription
    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val shouldClose = MutableLiveData<Boolean>()

    init {
        storeSubscription = store.subscribe { }
        val state = RdxSongDetailsState.selectFrom(store.state)
        currentSong = state.currentSong.toSong()
        title.postValue(currentSong.title)
        content.postValue(currentSong.content)
    }

    fun saveSong(title: String, content: String) {
        store.dispatch(SaveSong(RdxSong.from(currentSong)
            .copy(title = title, content = content)))
    }

//    override fun newState(state: SongDetailsState) {
//        state.currentSong.let {
//            title.postValue(it.title)
//            content.postValue(it.content)
//        }
//    }

    override fun onCleared() {
        super.onCleared()
    }
}
