package com.loop.yale.east.musicapp.Fragment

import com.loop.yale.east.musicapp.MainActivity.FRAGMENT_LIST

/**
 * Created by tatsuhiko on 2017/07/15.
 */
interface FragmentInteractionInterface {

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
     fun onFragmentInteraction(nextFragment: FRAGMENT_LIST)
}