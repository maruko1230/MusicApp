package com.loop.yale.east.musicapp

import android.Manifest
import android.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.loop.yale.east.musicapp.Contents.MusicItem
import com.loop.yale.east.musicapp.Fragment.ArtistFragment
import com.loop.yale.east.musicapp.Fragment.ArtistFragment.OnListFragmentInteractionListener
import com.loop.yale.east.musicapp.Fragment.CategorySelectionFragment
import com.loop.yale.east.musicapp.Fragment.FragmentInteractionInterface
import com.loop.yale.east.musicapp.Fragment.MusicViewModel

class MainActivity : AppCompatActivity(), FragmentInteractionInterface, OnListFragmentInteractionListener{
    private val TAG = "MainActivity"

    private val PERMISSION_REQUEST_ID = 100

    private var mMusicViewModel: MusicViewModel? = null

    enum class FRAGMENT_LIST{
        CATEGORY,
        ALBUM,
        ARTIST,
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMusicViewModel = ViewModelProviders.of(this).get(MusicViewModel::class.java)
        setContentView(R.layout.activity_main)
        switchFragment(FRAGMENT_LIST.CATEGORY)
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume")
        if (checkPermission()) {
            (mMusicViewModel as MusicViewModel).loadDataBase(this)
        }
    }

    fun switchFragment(nextFragment: FRAGMENT_LIST) {
        val fragManager = fragmentManager
        val fragmentTransaction = fragManager.beginTransaction()
        val fragment = FragmentFactory.create(nextFragment)

        if (fragment != null) {
            fragmentTransaction.replace(R.id.fragment_container, fragment)
            //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            fragmentTransaction.commit()
        } else {
            Log.e(TAG, "Unknown target fragment!")
        }
    }

    companion object FragmentFactory {
        fun create(nextFragment: FRAGMENT_LIST): Fragment? {
            var fragment: Fragment? = null
            when(nextFragment) {
                FRAGMENT_LIST.CATEGORY -> fragment = CategorySelectionFragment()
                FRAGMENT_LIST.ARTIST -> fragment = ArtistFragment()
            }
            return fragment
        }
    }

    override fun onFragmentInteraction(nextFragment: FRAGMENT_LIST) {
        Log.e("XXXXX", "onFragmentInteraction: " + nextFragment.name)
        switchFragment(nextFragment)

    }

    override fun onListFragmentInteraction(item: MusicItem) {
        Log.e(TAG, "item is selected: " + item.title)
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
}
