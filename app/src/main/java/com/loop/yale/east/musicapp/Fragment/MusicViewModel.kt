package com.loop.yale.east.musicapp.Fragment

import android.arch.lifecycle.ViewModel
import android.content.ContentProvider
import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
import android.util.Log
import com.loop.yale.east.musicapp.Contents.MusicContents
import com.loop.yale.east.musicapp.Contents.MusicItem
import java.util.concurrent.ExecutorService

/**
 * Created by tatsuhiko on 2017/07/15.
 */

class MusicViewModel(): ViewModel() {

    val TAG = "MusicViewModel"

    var mBackgroundThread: HandlerThread? = null
    var mBackgroundThreadHandler: Handler? = null

    init {
        Log.e(TAG, "Constructor")
        startBackgroundThread()
    }

    fun loadDataBase(context: Context) {
        Log.e(TAG, "loadDataBase")
        if (mBackgroundThreadHandler != null) {
            (mBackgroundThreadHandler as Handler).post(object : Runnable {
                override fun run() {
                    Log.e(TAG, "Current thread = " + Thread.currentThread().name)
                }
            })

            if (MusicContents.size() < 1) {
                (mBackgroundThreadHandler as Handler).post(MediaStoreLoader(context))
            }
        }
    }

    inner class MediaStoreLoader(val context: Context): Runnable {
        override fun run() {
            Log.e(TAG, "run MediaStoreLoader")
            MusicContents.clearItems()

            val resolver = context.contentResolver
            val cursor = resolver.query(EXTERNAL_CONTENT_URI, null, null, null, null, null)

            Log.e(TAG, "List size = " + cursor.count)
            while( cursor.moveToNext() ){
                val artist = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST ))
                val title = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.TITLE ) )
                val order = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.TRACK ) ).toInt()

                Log.e(TAG , "====================================");
                Log.e(TAG , cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.ALBUM ) ) ); //アルバム名の取得
                Log.e(TAG , cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST ) ) ); //アーティスト名の取得
                Log.e(TAG , cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.TITLE ) ) ); //タイトルの取得
                Log.e(TAG , cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media._ID ) ) ); //タイトルの取得

                val musicItem = MusicItem(artist, title, 0)
                MusicContents.addItem(musicItem)
            }
            Log.e(TAG, "Size of contents list = " + MusicContents.ITEMS.size)

        }
    }

    override fun onCleared() {
        super.onCleared()
        stopBackgroundThread()
    }

    private fun startBackgroundThread() {
        mBackgroundThread = HandlerThread(TAG)
        (mBackgroundThread as HandlerThread).start()
        mBackgroundThreadHandler = Handler((mBackgroundThread as HandlerThread).getLooper())
    }

    private fun stopBackgroundThread() {
        if(mBackgroundThread != null && mBackgroundThread is HandlerThread) {
            val backgroundThread = mBackgroundThread as HandlerThread
            backgroundThread.quitSafely()
            try {
                backgroundThread.join()
                mBackgroundThread = null
                mBackgroundThreadHandler = null
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

}
