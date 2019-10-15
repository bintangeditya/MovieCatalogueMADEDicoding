package id.editya.thesubmissionbe.movie


import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import id.editya.thesubmissionbe.MainActivity
import id.editya.thesubmissionbe.R
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelMovie
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment(), DataSource.MovieCallBack {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var rvMovie: RecyclerView
    private lateinit var movieAdapter: ItemMovieAdapter
    private lateinit var list: List<ModelMovie>
    private lateinit var ivLoading: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rvMovie = view.findViewById(R.id.rv_tvshow)
        rvMovie.setHasFixedSize(true)

        list = ArrayList()

        ivLoading = view.findViewById(R.id.iv_loading)
        Glide.with(this).load(R.drawable.ellipsis).into(iv_loading)
        iv_loading.visibility = INVISIBLE


        movieAdapter = ItemMovieAdapter(activity as Context, list as ArrayList<ModelMovie>)

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        movieViewModel.setCtx(this.activity!!.applicationContext)

        when {
            movieViewModel.listMovie.isEmpty() -> {
                iv_loading.visibility = VISIBLE
                movieViewModel.getMovie(this, resources.getString(R.string.code_lang))
                blockUserTouch(true)
            }
            movieViewModel.langOfList != resources.getString(R.string.code_lang) -> {
                iv_loading.visibility = VISIBLE
                movieViewModel.getMovie(this, resources.getString(R.string.code_lang))
                blockUserTouch(true)
            }
            else -> {
                movieAdapter.arrList = movieViewModel.listMovie
                movieAdapter.notifyDataSetChanged()
            }
        }

        rvMovie.layoutManager = LinearLayoutManager(activity as Context)
        rvMovie.adapter = movieAdapter
    }

    override fun onSuccessMovie(data: List<ModelMovie>?) {
        movieAdapter.arrList.addAll(data!!)

        movieAdapter.notifyDataSetChanged()
        ivLoading.visibility = INVISIBLE

        blockUserTouch(false)
    }

    @SuppressLint("ShowToast")
    override fun onFailureMovie(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
        ivLoading.visibility = INVISIBLE

        blockUserTouch(false)
    }

    private fun blockUserTouch(b: Boolean) {
        val mainActivity = activity as MainActivity
        mainActivity.mainViewModel.blocked = b
      }

}
