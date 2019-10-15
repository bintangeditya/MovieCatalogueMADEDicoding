package id.editya.thesubmissionbe

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.BottomNavigationView
import android.view.View
import id.editya.thesubmissionbe.fav.FavFragment
import id.editya.thesubmissionbe.movie.MovieFragment
import id.editya.thesubmissionbe.remider.ReminderFragment
import id.editya.thesubmissionbe.search.SearchFragment
import id.editya.thesubmissionbe.tvshow.TVShowFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Utils.makeSharedPreferences(this)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.injection(this)

        when {
            mainViewModel.state == "" -> {
                mainViewModel.state = "movie"
                custom_appbar.visibility = View.VISIBLE
                supportFragmentManager.beginTransaction().replace(
                    R.id.fl_container,
                    MovieFragment()
                ).commit()
            }
            mainViewModel.state == "movie" -> custom_appbar.visibility = View.VISIBLE
            mainViewModel.state == "tvshow" -> custom_appbar.visibility = View.VISIBLE
            mainViewModel.state == "fav" -> custom_appbar.visibility = View.GONE
            mainViewModel.state == "search" -> custom_appbar.visibility = View.GONE
            mainViewModel.state == "reminder" -> custom_appbar.visibility = View.VISIBLE

        }

        btn_language.setOnClickListener { setLanguage() }

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            if (!mainViewModel.blocked) {
                when (it.itemId) {
                    99 -> {
                        Utils.showToast(this, resources.getString(R.string.wait), 1000)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_movie -> {
                        mainViewModel.state = "movie"
                        custom_appbar.visibility = View.VISIBLE
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fl_container,
                            MovieFragment()
                        ).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_tv -> {
                        mainViewModel.state = "tvshow"
                        custom_appbar.visibility = View.VISIBLE
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fl_container,
                            TVShowFragment()
                        ).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_fav -> {
                        mainViewModel.state = "fav"
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fl_container,
                            FavFragment()
                        ).commit()
                        custom_appbar.visibility = View.GONE
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_serch -> {
                        mainViewModel.state = "search"
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fl_container,
                            SearchFragment()
                        ).commit()
                        custom_appbar.visibility = View.GONE
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_reminder -> {
                        mainViewModel.state = "reminder"
                        custom_appbar.visibility = View.VISIBLE
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fl_container,
                            ReminderFragment()
                        ).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    else -> false
                }
            } else {
                Utils.showToast(this, resources.getString(R.string.wait), 1000)
                return@OnNavigationItemSelectedListener false
            }
        })
    }

    private fun setLanguage() {
        if (!mainViewModel.blocked) {
            val intentLocaleSettings = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intentLocaleSettings)
        } else Utils.showToast(this, resources.getString(R.string.wait), 1000)
    }
}
