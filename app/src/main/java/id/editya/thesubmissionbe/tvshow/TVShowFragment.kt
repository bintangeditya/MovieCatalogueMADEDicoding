package id.editya.thesubmissionbe.tvshow


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
import android.widget.Toast
import com.bumptech.glide.Glide
import id.editya.thesubmissionbe.MainActivity
import id.editya.thesubmissionbe.R
import id.editya.thesubmissionbe.data.DataSource
import id.editya.thesubmissionbe.model.ModelTVShow
import kotlinx.android.synthetic.main.fragment_tvshow.*


/**
 * A simple [Fragment] subclass.
 *
 */
class TVShowFragment : Fragment(), DataSource.TVShowCallBack {

    private lateinit var tvshowViewModel: TVShowViewModel
    private lateinit var rvTVShow: RecyclerView
    private lateinit var tvshowAdapter: ItemTVShowAdapter
    private lateinit var list: List<ModelTVShow>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTVShow = view.findViewById(R.id.rv_tvshow)
        rvTVShow.setHasFixedSize(true)

        list = ArrayList()

        Glide.with(this).load(R.drawable.ellipsis).into(iv_loading)
        iv_loading.visibility = INVISIBLE

        tvshowAdapter = ItemTVShowAdapter(activity as Context, list as ArrayList<ModelTVShow>)

        tvshowViewModel = ViewModelProviders.of(this).get(TVShowViewModel::class.java)
        tvshowViewModel.setCtx(this.activity!!.applicationContext)

        when {
            tvshowViewModel.listTVShow.isEmpty() -> {
                iv_loading.visibility = VISIBLE
                tvshowViewModel.getTVShow(this, resources.getString(R.string.code_lang))
                blockUserTouch(true)
            }
            tvshowViewModel.langOfList != resources.getString(R.string.code_lang) -> {
                iv_loading.visibility = VISIBLE
                tvshowViewModel.getTVShow(this, resources.getString(R.string.code_lang))
                blockUserTouch(true)
            }
            else -> {
                tvshowAdapter.arrList = tvshowViewModel.listTVShow
                tvshowAdapter.notifyDataSetChanged()
            }
        }


        rvTVShow.layoutManager = LinearLayoutManager(activity as Context)
        rvTVShow.adapter = tvshowAdapter

    }

    override fun onSuccessTVShow(data: List<ModelTVShow>?) {
        tvshowAdapter.arrList.addAll(data!!)
        tvshowAdapter.notifyDataSetChanged()
        iv_loading.visibility = INVISIBLE

        blockUserTouch(false)
    }

    @SuppressLint("ShowToast")
    override fun onFailureTVShow(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
        iv_loading.visibility = INVISIBLE

        blockUserTouch(false)
    }

    private fun blockUserTouch(b: Boolean) {
        val mainActivity = activity as MainActivity
        mainActivity.mainViewModel.blocked = b
    }

}
