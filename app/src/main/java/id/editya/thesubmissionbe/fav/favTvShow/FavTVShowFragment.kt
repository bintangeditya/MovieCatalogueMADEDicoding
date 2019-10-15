package id.editya.thesubmissionbe.fav.favTvShow


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import id.editya.thesubmissionbe.R
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelTVShow
import kotlinx.android.synthetic.main.fragment_fav_tvshow.*
import kotlinx.android.synthetic.main.fragment_tvshow.iv_loading


class FavTVShowFragment : Fragment(), DataSource.TVShowCallBack {


    private lateinit var favTvshowViewModel: FavTVShowViewModel
    private lateinit var rvFavTvshow: RecyclerView
    private lateinit var favTvshowAdapter: ItemFavTVShowAdapter
    private lateinit var list: List<ModelTVShow>
    private lateinit var ivLoading: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_tvshow, container, false)
    }

    companion object {
        fun newInstance(): FavTVShowFragment = FavTVShowFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFavTvshow = view.findViewById(R.id.rv_fav_tvshow)
        rvFavTvshow.setHasFixedSize(true)

        list = ArrayList()

        ivLoading = view.findViewById(R.id.iv_loading)
        Glide.with(this).load(R.drawable.ellipsis).into(iv_loading)
        iv_loading.visibility = View.INVISIBLE

        favTvshowAdapter = ItemFavTVShowAdapter(activity as Context, list as ArrayList<ModelTVShow>, this)
        favTvshowAdapter.langOfList = resources.getString(R.string.code_lang)

        favTvshowViewModel = ViewModelProviders.of(this).get(FavTVShowViewModel::class.java)
        favTvshowViewModel.setCtx(this.activity!!.applicationContext)


        if (favTvshowViewModel.listFavTVShow.isEmpty()) {
            iv_loading.visibility = View.VISIBLE
            favTvshowViewModel.getFavTVShow(this)
        } else {
            favTvshowAdapter.arrList = favTvshowViewModel.listFavTVShow
            favTvshowAdapter.notifyDataSetChanged()
            fav_note.visibility = View.INVISIBLE
        }


        rvFavTvshow.layoutManager = LinearLayoutManager(activity as Context)
        rvFavTvshow.adapter = favTvshowAdapter

    }

    override fun onSuccessTVShow(data: List<ModelTVShow>?) {
        favTvshowAdapter.arrList.clear()
        favTvshowAdapter.arrList.addAll(data!!)
        favTvshowAdapter.notifyDataSetChanged()
        ivLoading.visibility = View.INVISIBLE
        fav_note.visibility = View.INVISIBLE
    }

    override fun onFailureTVShow(message: String?) {
        Log.d("asdasdfail", message)
        ivLoading.visibility = View.INVISIBLE
    }

    fun delete(p: Int) {
        val data = favTvshowViewModel.listFavTVShow[p]
        favTvshowAdapter.arrList.removeAt(p)
        favTvshowAdapter.notifyDataSetChanged()
        favTvshowViewModel.delete(data, p)
        if (favTvshowAdapter.arrList.isEmpty()) fav_note.visibility = View.VISIBLE

    }


}
