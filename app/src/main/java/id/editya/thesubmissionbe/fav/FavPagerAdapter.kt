package id.editya.thesubmissionbe.fav

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import id.editya.thesubmissionbe.fav.favMovie.FavMovieFragment
import id.editya.thesubmissionbe.fav.favTvShow.FavTVShowFragment

class FavPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? = when (position) {
        0 -> FavMovieFragment.newInstance()
        1 -> FavTVShowFragment.newInstance()
        else -> null
    }

    override fun getPageTitle(position: Int): CharSequence = when (position) {
        0 -> "Movie"
        1 -> "TV Show"
        else -> ""
    }

    override fun getCount(): Int = 2
}