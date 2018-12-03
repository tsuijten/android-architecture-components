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
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat


/**
 * Shows a profile screen for a user, taking the name from the arguments.
 */
class UserProfile : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.shared_element)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.shared_element)

        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)

        val avatar = arguments?.getInt("avatar") ?: 0
        val name = arguments?.getString("userName") ?: "Ali Connors"
        val avatarView = view.findViewById<ImageView>(R.id.profile_user_avatar)
        avatarView.setImageResource(avatar)
        ViewCompat.setTransitionName(avatarView, name)
        view.findViewById<TextView>(R.id.profile_user_name).text = name
        return view
    }
}
