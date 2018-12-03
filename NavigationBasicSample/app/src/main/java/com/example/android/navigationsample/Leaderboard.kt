/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigationsample

import android.os.Bundle
import android.transition.Explode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView

/**
 * Shows a static leaderboard with three users.
 */
class Leaderboard : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_leaderboard, container, false)

//        postponeEnterTransition()

        viewAdapter = MyAdapter(this, arrayOf("Flo", "Ly", "Jo"))

        recyclerView = view.findViewById<RecyclerView>(R.id.leaderboard_list).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        // Fixes weird reenter transition, but causes problems when popping
//        view.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
//            override fun onPreDraw(): Boolean {
//                view.viewTreeObserver.removeOnPreDrawListener(this)
//                startPostponedEnterTransition()
//                return true
//            }
//        })

        return view

    }

}

class MyAdapter(private val fragment: Fragment, private val myDataset: Array<String>) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val item: View) : RecyclerView.ViewHolder(item)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_view_item, parent, false)


        return ViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val userName = myDataset[position]
        holder.item.findViewById<TextView>(R.id.user_name_text).text = userName

        val avatarImageView = holder.item.findViewById<ImageView>(R.id.user_avatar_image)
        avatarImageView.setImageResource(listOfAvatars[position])
        ViewCompat.setTransitionName(avatarImageView, userName)

        holder.item.setOnClickListener {

            val explode = Explode()

            fragment.reenterTransition = explode
            fragment.enterTransition = explode

            val bundle = bundleOf(
                    "userName" to userName,
                    "avatar" to listOfAvatars[position]
            )

            val extras = FragmentNavigatorExtras(avatarImageView to userName)

            Navigation.findNavController(holder.item).navigate(
                    R.id.action_leaderboard_to_userProfile,
                    bundle,
                    null,
                    extras
            )
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}

private val listOfAvatars = listOf(
        R.drawable.avatar_1_raster, R.drawable.avatar_2_raster, R.drawable.avatar_3_raster)
