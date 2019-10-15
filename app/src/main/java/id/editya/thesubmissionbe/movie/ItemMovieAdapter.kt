package id.editya.thesubmissionbe.movie

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
import id.editya.thesubmissionbe.model.ModelMovie
import kotlinx.android.synthetic.main.item_list.view.*

class ItemMovieAdapter(private val context: Context, list: ArrayList<ModelMovie>) :
    RecyclerView.Adapter<ItemMovieAdapter.CardViewHolder>() {

    var arrList: ArrayList<ModelMovie> = list


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardViewHolder {

        return CardViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    override fun onBindViewHolder(p0: CardViewHolder, p1: Int) {
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w154/" + arrList[p1].PosterPath)
            .into(p0.ivPoster)

        p0.tvTitle.text = arrList[p1].Title
        p0.tvYear.text = arrList[p1].ReleaseDate.substring(0, 4)

        p0.itemStNumb.text = arrList[p1].VoteAverage.toString()

        val rD = arrList[p1].VoteAverage / 2
        p0.ratingBar.rating = rD.toFloat()

        p0.clParent.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(Utils.INTENTDATA, arrList[p1])
            intent.putExtra(Utils.FROM, "movie")
            intent.putExtra(Utils.FROMFAV,"0")
            context.startActivity(intent)
        }
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clParent: ConstraintLayout = itemView.cl_parent
        val ivPoster: ImageView = itemView.iv_poster
        val tvTitle: TextView = itemView.tv_title
        val tvYear: TextView = itemView.tv_year
        val itemStNumb: TextView = itemView.item_st_numb
        val ratingBar: RatingBar = itemView.ratingBarList
    }
}