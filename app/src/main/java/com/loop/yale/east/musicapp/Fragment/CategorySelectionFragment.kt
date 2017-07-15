package com.loop.yale.east.musicapp.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.loop.yale.east.musicapp.MainActivity
import com.loop.yale.east.musicapp.MainActivity.FRAGMENT_LIST

import com.loop.yale.east.musicapp.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CategorySelectionFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CategorySelectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategorySelectionFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    //private var mListener: OnFragmentInteractionListener? = null
    private var mListener: FragmentInteractionInterface ? = null

    private var mActivityViewModel: MusicViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }

        mActivityViewModel = ViewModelProviders.of(activity as FragmentActivity).get(MusicViewModel::class.java)
    }

    private var mArtistButton: Button? = null
    private var mAlbumButton: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val selectionView = inflater.inflate(R.layout.fragment_category_selection, container, false)

        val clickListener = OnClickListener()

        var artistButton = selectionView.findViewById(R.id.category_selector_artist) as Button
        artistButton.setOnClickListener(clickListener)
        mArtistButton = artistButton

        mAlbumButton = selectionView.findViewById(R.id.category_selector_album) as Button
        (mAlbumButton as Button).setOnClickListener(clickListener)

        return selectionView
        //return inflater.inflate(R.layout.fragment_category_selection, container, false)
    }

    inner class OnClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            var selectedButton: FRAGMENT_LIST = FRAGMENT_LIST.CATEGORY
            if (v != null) {
                when(v.id) {
                    R.id.category_selector_album -> selectedButton = FRAGMENT_LIST.ALBUM
                    R.id.category_selector_artist -> selectedButton = FRAGMENT_LIST.ARTIST
                }
            }

            Log.e("XXXXX", "onClick: " + selectedButton.name)
            if (selectedButton != FRAGMENT_LIST.CATEGORY && mListener != null) {
                (mListener as FragmentInteractionInterface).onFragmentInteraction(selectedButton)
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentInteractionInterface) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
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
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(nextFragment: MainActivity.FRAGMENT_LIST)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment CategorySelectionFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): CategorySelectionFragment {
            val fragment = CategorySelectionFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
