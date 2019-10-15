package id.editya.thesubmissionbe.fav.favMovie


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import id.editya.thesubmissionbe.R
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelMovie
import kotlinx.android.synthetic.main.fragment_fav_movie.fav_note
import kotlinx.android.synthetic.main.fragment_tvshow.iv_loading


class FavMovieFragment : Fragment(), DataSource.MovieCallBack {


    private lateinit var favMovieViewModel: FavMovieViewModel
    private lateinit var rvFavMovie: RecyclerView
    private lateinit var favMovieAdapter: ItemFavMovieAdapter
    private lateinit var list: List<ModelMovie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_movie, container, false)
    }

    companion object {
        fun newInstance(): FavMovieFragment = FavMovieFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFavMovie = view.findViewById(R.id.rv_fav_movie)
        rvFavMovie.setHasFixedSize(true)

        list = ArrayList()

        Glide.with(this).load(R.drawable.ellipsis).into(iv_loading)
        iv_loading.visibility = View.INVISIBLE

        favMovieAdapter = ItemFavMovieAdapter(activity as Context, list as ArrayList<ModelMovie>, this)
        favMovieAdapter.langOfList = resources.getString(R.string.code_lang)

        favMovieViewModel = ViewModelProviders.of(this).get(FavMovieViewModel::class.java)
        favMovieViewModel.setCtx(this.activity!!.applicationContext)



        if (favMovieViewModel.listFavMovie.isEmpty()) {
            iv_loading.visibility = View.VISIBLE
            favMovieViewModel.getFavMovie(this)
        } else {
            favMovieAdapter.arrList = favMovieViewModel.listFavMovie
            favMovieAdapter.notifyDataSetChanged()
            fav_note.visibility = View.INVISIBLE
        }


        rvFavMovie.layoutManager = LinearLayoutManager(activity as Context)
        rvFavMovie.adapter = favMovieAdapter

    }


    override fun onSuccessMovie(data: List<ModelMovie>?) {
        favMovieAdapter.arrList.clear()
        favMovieAdapter.arrList.addAll(data!!)
        favMovieAdapter.notifyDataSetChanged()
        iv_loading.visibility = View.INVISIBLE
        fav_note.visibility = View.INVISIBLE
    }

    override fun onFailureMovie(message: String?) {
        iv_loading.visibility = View.INVISIBLE
    }

    fun delete(p: Int) {
        val data = favMovieViewModel.listFavMovie[p]
        favMovieAdapter.arrList.removeAt(p)
        favMovieAdapter.notifyDataSetChanged()
        favMovieViewModel.delete(data,p)
        if (favMovieAdapter.arrList.isEmpty()) fav_note.visibility = View.VISIBLE
    }
}
