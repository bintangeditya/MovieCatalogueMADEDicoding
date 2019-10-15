package id.editya.thesubmissionbe.search


import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import id.editya.thesubmissionbe.MainActivity
import id.editya.thesubmissionbe.R
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelMovie
import id.editya.thesubmissionbe.model.ModelTVShow
import kotlinx.android.synthetic.main.fragment_movie.iv_loading

class SearchFragment : Fragment(), DataSource.SearchMovieCallBack, DataSource.SearchTVShowCallBack {


    lateinit var searchViewModel: SearchViewModel
    lateinit var rvSearch: RecyclerView
    lateinit var searchMovieAdapter: ItemSearchMovieAdapter
    lateinit var searchTVShowAdapter: ItemSearchTVShowAdapter
    lateinit var listMovie: List<ModelMovie>
    lateinit var listTVShow: List<ModelTVShow>
    lateinit var ivLoading: ImageView
    lateinit var svCustom: SearchView
    lateinit var btnSearchMovie: TextView
    lateinit var btnSearchTVShow: TextView
    private val MOVIE = "MOVIE"
    private val TVSHOW = "TVSHOW"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvSearch = view.findViewById(R.id.rc_search)
        rvSearch.setHasFixedSize(true)
        rvSearch.layoutManager = LinearLayoutManager(activity as Context)

        svCustom = view.findViewById(R.id.sv_custom)

        btnSearchMovie = view.findViewById<TextView>(R.id.btn_search_movie)
        btnSearchTVShow = view.findViewById<TextView>(R.id.btn_search_tvshow)


        listMovie = ArrayList()
        listTVShow = ArrayList()

        ivLoading = view.findViewById(R.id.iv_loading)
        Glide.with(this).load(R.drawable.ellipsis).into(iv_loading)
        iv_loading.visibility = View.INVISIBLE

        searchMovieAdapter = ItemSearchMovieAdapter(activity as Context, listMovie as ArrayList<ModelMovie>)
        searchTVShowAdapter = ItemSearchTVShowAdapter(
            activity as Context,
            listTVShow as ArrayList<ModelTVShow>
        )

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        searchViewModel.setCtx(this.activity!!.applicationContext)

        btnSearchMovie.setOnClickListener {
            searchViewModel.state = MOVIE
            buttonSelected()
            fecthData(searchViewModel.lastQuery)
        }
        btnSearchTVShow.setOnClickListener {
            searchViewModel.state = TVSHOW
            buttonSelected()
            fecthData(searchViewModel.lastQuery)
        }
        buttonSelected()
        svCustom.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                var text = ""
                if (!query.isNullOrEmpty()) text = query
                fecthData(text)
                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                var text = ""
                if (!newText.isNullOrEmpty()) text = newText
                fecthData(text)
                return false
            }

        })
    }


    override fun onSuccessMovie(data: List<ModelMovie>?, amount: Int) {
        if (!data.isNullOrEmpty()) {
            var size = 20
            if (amount < 20) size = amount
            searchMovieAdapter.sizeSearchMovie = size
            searchMovieAdapter.arrList.clear()
            searchMovieAdapter.arrList.addAll(data)
        } else {
            searchMovieAdapter.sizeSearchMovie = 0
            searchMovieAdapter.arrList.clear()
        }
        searchMovieAdapter.notifyDataSetChanged()
        rvSearch.adapter = searchMovieAdapter
        ivLoading.visibility = View.INVISIBLE
        blockUserTouch(false)
    }

    @SuppressLint("ShowToast")
    override fun onFailureMovie(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
        searchMovieAdapter.sizeSearchMovie = 0
        searchMovieAdapter.arrList.clear()
        searchMovieAdapter.notifyDataSetChanged()
        rvSearch.adapter = searchMovieAdapter
        ivLoading.visibility = View.INVISIBLE
        blockUserTouch(false)
    }

    override fun onSuccessTVShow(data: List<ModelTVShow>?, amount: Int) {
        if (!data.isNullOrEmpty()) {
            var size = 20
            if (amount < 20) size = amount
            searchTVShowAdapter.sizeSearchTVShow = size
            searchTVShowAdapter.arrList.clear()
            searchTVShowAdapter.arrList.addAll(data)
        } else {
            searchTVShowAdapter.sizeSearchTVShow = 0
            searchTVShowAdapter.arrList.clear()
        }
        searchTVShowAdapter.notifyDataSetChanged()
        iv_loading.visibility = View.INVISIBLE
        rvSearch.adapter = searchTVShowAdapter
        blockUserTouch(false)
    }

    @SuppressLint("ShowToast")
    override fun onFailureTVShow(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
        searchTVShowAdapter.sizeSearchTVShow = 0
        searchTVShowAdapter.arrList.clear()
        rvSearch.adapter = searchTVShowAdapter
        iv_loading.visibility = View.INVISIBLE
        blockUserTouch(false)
    }

    private fun blockUserTouch(b: Boolean) {
        val mainActivity = activity as MainActivity
        mainActivity.mainViewModel.blocked = b
    }

    fun fecthData(query: String) {
        if (searchViewModel.state == MOVIE) {

            when {
                query != searchViewModel.lastQueryMovie -> {
                    iv_loading.visibility = View.VISIBLE
                    searchViewModel.searchMovie(this, resources.getString(R.string.code_lang), query)
                    blockUserTouch(true)
                }
                searchViewModel.everHadMovie -> {
                    searchMovieAdapter.arrList = searchViewModel.listMovie
                    searchMovieAdapter.sizeSearchMovie = searchViewModel.sizeListMovie
                    searchMovieAdapter.notifyDataSetChanged()
                }
                else ->{}
            }


            rvSearch.adapter = searchMovieAdapter

        } else if (searchViewModel.state == TVSHOW) {


            when {
                query != searchViewModel.lastQueryTVShow -> {
                    iv_loading.visibility = View.VISIBLE
                    searchViewModel.searchTVShow(this, resources.getString(R.string.code_lang), query)
                    blockUserTouch(true)
                }
                searchViewModel.everHadTVShow -> {
                    searchTVShowAdapter.arrList = searchViewModel.listTVShow
                    searchTVShowAdapter.sizeSearchTVShow = searchViewModel.sizeListTVShow
                    searchTVShowAdapter.notifyDataSetChanged()
                }
                else ->{}
            }


            rvSearch.adapter = searchTVShowAdapter

        }
    }

    private fun buttonSelected() {
        if (searchViewModel.state == MOVIE) {
            btnSearchMovie.setTextColor(resources.getColor(R.color.mWhite))
            btnSearchMovie.background = resources.getDrawable(R.drawable.bg_rounded_blue)
            btnSearchTVShow.setTextColor(resources.getColor(R.color.mBlack))
            btnSearchTVShow.background = resources.getDrawable(R.drawable.bg_rounded_grey)
        } else if (searchViewModel.state == TVSHOW) {
            btnSearchTVShow.setTextColor(resources.getColor(R.color.mWhite))
            btnSearchTVShow.background = resources.getDrawable(R.drawable.bg_rounded_blue)
            btnSearchMovie.setTextColor(resources.getColor(R.color.mBlack))
            btnSearchMovie.background = resources.getDrawable(R.drawable.bg_rounded_grey)
        }
    }

}
