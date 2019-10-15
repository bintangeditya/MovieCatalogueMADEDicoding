package id.editya.thesubmissionbe

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelMovie
import id.editya.thesubmissionbe.model.ModelTVShow
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.item_st_numb
import kotlinx.android.synthetic.main.activity_detail.iv_poster
import kotlinx.android.synthetic.main.activity_detail.tv_title


class DetailActivity : AppCompatActivity(), DataSource.OverviewMovieCallback, DataSource.OverviewTVShowCallback {

    override fun onSuccessMovie(oMovies: List<String>?) {
        val data = intent.getParcelableExtra<ModelMovie>(Utils.INTENTDATA)
        data.OverviewEn = oMovies!![0]
        data.OverviewId = oMovies[1]
        dataSource.saveFavMovie(data)
        fav(data.Fav == 0)
        loadingState = false
        Utils.showToast(this, resources.getString(R.string.succeed_fav), 1000)
    }

    override fun onSuccessTVShow(oTVShows: List<String>?) {
        val data = intent.getParcelableExtra<ModelTVShow>(Utils.INTENTDATA)
        data.OverviewEn = oTVShows!![0]
        data.OverviewId = oTVShows[1]
        dataSource.saveFavTvshow(data)
        fav(data.Fav == 0)
        loadingState = false
        Utils.showToast(this, resources.getString(R.string.succeed_fav), 1000)
    }


    private val dataSource = DataSource(this)
    private var loadingState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (intent.getStringExtra(Utils.FROM) == "tvshow") {

            var data = intent.getParcelableExtra<ModelTVShow>(Utils.INTENTDATA)

            dataSource.checkTvshow(data.Id.toString(), object : DataSource.CheckTvshowCallback {
                override fun onResult(r: Int) {
                    if (r > 0) {
                        data.Fav = 1
                        btn_fav.setImageResource(R.drawable.favorite_true)
                    } else data.Fav = 0
                }
            })

            if (data.Fav == null) data.Fav = 0
            if (data.Fav == 1) btn_fav.setImageResource(R.drawable.favorite_true)
            else btn_fav.setImageResource(R.drawable.favorite_false)

            btn_fav.setOnClickListener {
                if (data.Fav == 1) {
                    fav(data.Fav == 1)
                    dataSource.deleteFavTvshow(data)
                    Utils.showToast(this, resources.getString(R.string.delete_fav), 1000)

                    data.Fav = 0
                } else {
                    data.Fav = 1
                    dataSource.getOverviewTVShow(this, data.Id.toString())
                    Glide.with(this)
                        .load(R.drawable.ellipsis_red)
                        .into(btn_fav)
                    loadingState = true
                }
            }

            Glide.with(this)
                .load(R.drawable.ellipsis_white)
                .into(loading_image)


            var rimage = ""
            if (!data.PosterPath.isNullOrEmpty()) rimage = data.PosterPath!!
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/$rimage")
                .error(R.drawable.not_found_image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?, model: Any?, target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loading_image.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loading_image.visibility = View.GONE
                        return false
                    }
                })
                .into(iv_poster)

            if (!data.Name.isNullOrEmpty()) tv_title.text = data.Name
            if(!data.FirstAirDate.isNullOrEmpty()) tv_release.text = data.FirstAirDate
            if(!data.OriginalLanguage.isNullOrEmpty()) tv_lang.text = data.OriginalLanguage

            if (intent.getStringExtra(Utils.FROMFAV) == "1"){
                btn_fav.visibility = View.GONE
                if (resources.getString(R.string.code_lang) == "en-US") {
                    tv_ov.text = data.OverviewEn
                } else tv_ov.text = data.OverviewId
            }else tv_ov.text = data.Overview

            if (tv_ov.text.isNullOrEmpty()) tv_ov.text = resources.getString(R.string.no_overview)

            item_st_numb.text = data.VoteAverage.toString()

            val rD = data.VoteAverage / 2
            ratingBar.rating = rD.toFloat()


        } else if (intent.getStringExtra(Utils.FROM) == "movie") {

            var data = intent.getParcelableExtra<ModelMovie>(Utils.INTENTDATA)

            dataSource.checkMovie(data.Id.toString(), object : DataSource.CheckMovieCallback {
                override fun onResult(r: Int) {
                    if (r > 0) {
                        data.Fav = 1
                        btn_fav.setImageResource(R.drawable.favorite_true)
                    } else data.Fav = 0
                }

            })

            if (data.Fav == null) data.Fav = 0
            if (data.Fav == 1) btn_fav.setImageResource(R.drawable.favorite_true)
            else btn_fav.setImageResource(R.drawable.favorite_false)

            btn_fav.setOnClickListener {
                if (data.Fav == 1) {
                    fav(data.Fav == 1)
                    dataSource.deleteFavMovie(data)
                    Utils.showToast(this, resources.getString(R.string.delete_fav), 1000)

                    data.Fav = 0
                } else {
                    data.Fav = 1
                    dataSource.getOverviewMovie(this, data.Id.toString())
                    Glide.with(this)
                        .load(R.drawable.ellipsis_red)
                        .into(btn_fav)
                    loadingState = true
                }
            }

            Glide.with(this)
                .load(R.drawable.ellipsis_white)
                .into(loading_image)

            var rimage = ""
            if(!data.PosterPath.isNullOrEmpty()) rimage = data.PosterPath!!
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/" + rimage)
                .error(R.drawable.not_found_image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?, model: Any?, target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loading_image.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loading_image.visibility = View.GONE
                        return false
                    }
                })
                .into(iv_poster)

            if(!data.Title.isNullOrEmpty()) tv_title.text = data.Title
            if(!data.ReleaseDate.isNullOrEmpty()) tv_release.text = data.ReleaseDate
            if(!data.OriginalLanguage.isNullOrEmpty()) tv_lang.text = data.OriginalLanguage

            if (intent.getStringExtra(Utils.FROMFAV) == "1"){
                btn_fav.visibility = View.GONE
                if (resources.getString(R.string.code_lang) == "en-US") {
                    tv_ov.text = data.OverviewEn
                } else tv_ov.text = data.OverviewId
            }else tv_ov.text = data.Overview

            if (tv_ov.text.isNullOrEmpty()) tv_ov.text = resources.getString(R.string.no_overview)

            item_st_numb.text = data.VoteAverage.toString()

            val rD = data.VoteAverage / 2
            ratingBar.rating = rD.toFloat()
        }
    }

    private fun fav(fav: Boolean) {
        if (fav) {
            btn_fav.setImageResource(R.drawable.favorite_false)
        } else {
            btn_fav.setImageResource(R.drawable.favorite_true)
        }
    }

    override fun onBackPressed() {
        if (!loadingState) super.onBackPressed()
        else Utils.showToast(this, resources.getString(R.string.warn_loading_fav), 1000)
    }

}
