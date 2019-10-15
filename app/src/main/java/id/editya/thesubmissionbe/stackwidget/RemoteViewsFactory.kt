package id.editya.thesubmissionbe.stackwidget

import android.content.Context
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelMovie
import java.util.ArrayList
import com.bumptech.glide.Glide
import id.editya.thesubmissionbe.model.ModelTVShow


internal class RemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val listItem = ArrayList<Bitmap>()
    private val dataSource = DataSource(context)
    private val listMovieFav = ArrayList<ModelMovie>()
    private val listTVShow = ArrayList<ModelTVShow>()

    override fun onDataSetChanged() {
        dataSource.getFavMovieForWidget(object : DataSource.MovieCallBack {
            override fun onSuccessMovie(data: List<ModelMovie>?) {
                listMovieFav.clear()
                listMovieFav.addAll(data!!)
                listItem.clear()
                listMovieFav.forEach {
                    listItem.add(
                        Glide.with(context)
                            .asBitmap()
                            .load("https://image.tmdb.org/t/p/w342/" + it.PosterPath)
                            .submit()
                            .get()
                    )
                }
                dataSource.getFavTvshowForWidget(object : DataSource.TVShowCallBack {
                    override fun onSuccessTVShow(data: List<ModelTVShow>?) {
                        listTVShow.clear()
                        listTVShow.addAll(data!!)
                        listTVShow.forEach {
                            listItem.add(
                                Glide.with(context)
                                    .asBitmap()
                                    .load("https://image.tmdb.org/t/p/w342/" + it.PosterPath)
                                    .submit()
                                    .get()
                            )
                        }
                    }

                    override fun onFailureTVShow(message: String?) {
                    }
                })
            }

            override fun onFailureMovie(message: String?) {
            }
        })
    }

    override fun onCreate() {}

    override fun onDestroy() {}

    override fun getCount(): Int {
        return listItem.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val item = RemoteViews(context.packageName, id.editya.thesubmissionbe.R.layout.item_widget)
        item.setImageViewBitmap(id.editya.thesubmissionbe.R.id.imageView, listItem[position])
        return item
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }

}