package lv.zesloka.skani.presentation.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import lv.zesloka.skani.presentation.redux.ActionDispatcher
import lv.zesloka.skani.presentation.redux.AppStoreSubscriber
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.song.*
import javax.inject.Inject

open class SongViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    protected lateinit var storeSubscriber: AppStoreSubscriber

    @Inject
    protected lateinit var dispatcher: ActionDispatcher

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val shouldClose = MutableLiveData<Boolean>()



    fun init() {
        storeSubscriber.onRender { state -> render(state) }
    }

    fun saveSong(title: String, content: String) {
//        dispatcher.dispatch(SaveSong(RdxSong.from(currentSong)
//            .copy(title = title, content = content)))
    }


    override fun onCleared() {
        super.onCleared()
    }

    private fun render(state: RdxAppState) {
        val songState = RdxSongDetailsState.selectFrom(state)
        val currentSong = songState.currentSong.toSong()
        title.postValue(currentSong.title)
        content.postValue(currentSong.content)
    }
}
