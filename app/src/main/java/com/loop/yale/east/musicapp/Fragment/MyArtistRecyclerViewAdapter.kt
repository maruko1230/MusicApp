package com.loop.yale.east.musicapp.Fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.loop.yale.east.musicapp.Contents.MusicItem

import com.loop.yale.east.musicapp.Fragment.ArtistFragment.OnListFragmentInteractionListener
import com.loop.yale.east.musicapp.R

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyArtistRecyclerViewAdapter(private val mValues: List<MusicItem>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MyArtistRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return get()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_artist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mIdView.text = mValues[position].order.toString()
        holder.mContentView.text = mValues[position].artist
        holder.mDetailView.text = mValues[position].title

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction((holder.mItem as MusicItem))
        }
    }

    fun get() = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView
        val mContentView: TextView
        val mDetailView: TextView
        var mItem: MusicItem? = null

        init {
            mIdView = mView.findViewById(R.id.id) as TextView
            mContentView = mView.findViewById(R.id.content) as TextView
            mDetailView = mView.findViewById(R.id.detail) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
