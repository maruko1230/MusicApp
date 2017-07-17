package com.loop.yale.east.musicapp

import android.Manifest
import android.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.loop.yale.east.musicapp.Contents.MusicItem
import com.loop.yale.east.musicapp.Fragment.*
import com.loop.yale.east.musicapp.Fragment.ArtistFragment.OnArtistListFragmentInteractionListener

class MainActivity : AppCompatActivity(), FragmentInteractionInterface, OnArtistListFragmentInteractionListener{
    private val TAG = "MainActivity"

    private val PERMISSION_REQUEST_ID = 100

    private var mMusicViewModel: MusicViewModel? = null

    enum class FRAGMENT_LIST{
        CATEGORY,
        ALBUM,
        ARTIST,
        PLAYLIST,
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMusicViewModel = ViewModelProviders.of(this).get(MusicViewModel::class.java)
        setContentView(R.layout.activity_main)
        switchFragment(create(FRAGMENT_LIST.CATEGORY))
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume")
        if (checkPermission()) {
            (mMusicViewModel as MusicViewModel).loadDataBase(this)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
     /*   val transaction = supportFragmentManager.beginTransaction()
        transaction.detach().attach(activeFrag).commit()*/
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    fun switchFragment(fragment: Fragment) {
        val fragManager = fragmentManager
        val fragmentTransaction = fragManager.beginTransaction()

        if (fragment != null) {
            fragmentTransaction.replace(R.id.fragment_container, fragment)
            fragmentTransaction.addToBackStack(null)
            //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            fragmentTransaction.commit()
        } else {
            Log.e(TAG, "Unknown target fragment!")
        }
    }

    companion object FragmentFactory {
        fun create(nextFragment: FRAGMENT_LIST): Fragment {
            var fragment: Fragment = CategorySelectionFragment()
            when(nextFragment) {
                FRAGMENT_LIST.CATEGORY -> fragment = CategorySelectionFragment()
                FRAGMENT_LIST.ARTIST -> fragment = ArtistFragment()
            }
            return fragment
        }

        fun createArtist(): Fragment {
            val fragment = ArtistFragment()
            return fragment
        }

        fun createPlayList(playList: MutableList<MusicItem>): Fragment {
            val fragment = PlayListFragment(playList)
            return fragment
        }
    }

    override fun onFragmentInteraction(nextFragment: FRAGMENT_LIST) {
        Log.e("XXXXX", "onFragmentInteraction: " + nextFragment.name)
        switchFragment(create(nextFragment))
    }

    override fun onArtistListFragmentInteraction(item: MusicItem) {
        Log.e(TAG, "item is selected: " + item.artist)
        val playList = mMusicViewModel?.getArtistList(item.artist)

    }

    fun checkPermission(): Boolean {
        Log.e(TAG, "checkPermission")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Permission Granted")
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.e(TAG, "should request permission true")
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_ID)
                return false
            } else {
                Log.e(TAG, "should request permission false -> show runtime permission")
                //ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_ID)
                return true
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_REQUEST_ID -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "Permission granted!")
                    (mMusicViewModel as MusicViewModel).loadDataBase(this)
                } else {
                    Log.e(TAG, "Permission denied!")
                    finish()
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        Log.e(TAG, "Configuration Changed")
    }
}
