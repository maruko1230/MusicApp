package com.loop.yale.east.musicapp.Contents


import android.util.Log
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by tatsuhiko on 2017/07/15.
 */
object MusicContents {

    private val TAG = "MusicContents"
    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<MusicItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, MusicItem> = HashMap()


    fun addItem(music: MusicItem) {
        ITEMS.add(music)
    }

    fun clearItems() {
        val sizeBefore = ITEMS.size
        ITEMS.clear()
        Log.e(TAG, "clearItem: " + sizeBefore + " -> " + ITEMS.size)
    }

    fun size(): Int {
        return ITEMS.size
    }

}