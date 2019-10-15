package id.editya.thesubmissionbe.fav.favTvShow

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import id.editya.thesubmissionbe.DetailActivity
import id.editya.thesubmissionbe.R
import id.editya.thesubmissionbe.Utils
import id.editya.thesubmissionbe.model.ModelTVShow
import kotlinx.android.synthetic.main.item_list_fav.view.*

class ItemFavTVShowAdapter(private val context: Context, list: ArrayList<ModelTVShow>,private val fragment : FavTVShowFragment) :
    RecyclerView.Adapter<ItemFavTVShowAdapter.CardViewHolder>() {

    var arrList: ArrayList<ModelTVShow> = list
    lateinit var langOfList : String

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardViewHolder {

        return CardViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_fav,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    override fun onBindViewHolder(p0: CardViewHolder, p1: Int) {

        var rimage = ""
        if (!arrList[p1].PosterPath.isNullOrEmpty()) rimage = arrList[p1].PosterPath!!
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w154/" + rimage)
            .error(R.drawable.not_found_image)
            .into(p0.ivPoster)

        if (!arrList[p1].Name.isNullOrEmpty()) p0.tvTitle.text = arrList[p1].Name

        if (!arrList[p1].FirstAirDate.isNullOrEmpty()) p0.tvYear.text = arrList[p1].FirstAirDate.substring(0, 4)
        else arrList[p1].FirstAirDate = ""

        p0.itemStNumb.text = arrList[p1].VoteAverage.toString()

        val rD = arrList[p1].VoteAverage / 2
        p0.ratingBar.rating = rD.toFloat()

        p0.clParent.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(Utils.INTENTDATA, arrList[p1])
            intent.putExtra(Utils.FROM, "tvshow")
            intent.putExtra(Utils.FROMFAV,"1")
            intent.putExtra(Utils.POSITION,p1)
            context.startActivity(intent)
        }

        p0.deleteBtn.setOnClickListener {
            fragment.delete(p1)
        }
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clParent: ConstraintLayout = itemView.cl_parent
        val ivPoster: ImageView = itemView.iv_poster
        val tvTitle: TextView = itemView.tv_title
        val tvYear: TextView = itemView.tv_year
        val itemStNumb: TextView = itemView.item_st_numb
        val ratingBar: RatingBar = itemView.ratingBarList
        val deleteBtn: ImageView = itemView.btn_delete

    }
}