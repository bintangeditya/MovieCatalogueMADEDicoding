package id.editya.thesubmissionbe.tvshow

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import android.content.Context
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelTVShow

class TVShowViewModel : ViewModel() {
    val listTVShow = ArrayList<ModelTVShow>()
    lateinit var langOfList: String
    @SuppressLint("StaticFieldLeak")
    var context: Context? = null
    private var dataSource: DataSource? = null

    fun setCtx(context: Context){
        this.context=context
        dataSource = DataSource(context)
    }

    fun getTVShow(callBack: DataSource.TVShowCallBack, codeLang: String) {
        dataSource!!.getTVShow(object : DataSource.TVShowCallBack {
            override fun onSuccessTVShow(data: List<ModelTVShow>?) {
                listTVShow.clear()
                listTVShow.addAll(data as ArrayList)
                langOfList = codeLang
                callBack.onSuccessTVShow(listTVShow)
            }

            override fun onFailureTVShow(message: String?) {
                callBack.onFailureTVShow(message)
            }
        }, codeLang)
    }

}