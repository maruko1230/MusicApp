package com.loop.yale.east.musicapp.Fragment

import android.content.Context
import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loop.yale.east.musicapp.Contents.MusicContents
import com.loop.yale.east.musicapp.Contents.MusicItem

import com.loop.yale.east.musicapp.R

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class ArtistFragment : Fragment() {
    // TODO: Customize parameters
    private var mColumnCount = 1
    private var mListener: OnArtistListFragmentInteractionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_artist_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            val recyclerView = view
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(LinearLayoutManager(context))
            } else {
                recyclerView.setLayoutManager(GridLayoutManager(context, mColumnCount))
            }
            recyclerView.setAdapter(MyArtistRecyclerViewAdapter(MusicContents.ITEMS, mListener))
        }
        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnArtistListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnArtistListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onArtistListFragmentInteraction(item: MusicItem)
    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): ArtistFragment {
            val fragment = ArtistFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
