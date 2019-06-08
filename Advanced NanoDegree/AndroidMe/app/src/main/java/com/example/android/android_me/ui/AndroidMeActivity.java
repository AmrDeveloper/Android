/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.android_me.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

// This activity will display a custom Android image composed of three body parts: head, body, and legs
public class AndroidMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        //Create Fragment only if no saved instance state
        if(savedInstanceState == null){
            Intent intent = getIntent();
            Bundle information = intent.getExtras();

            //Fragment manager make easy to add, replace and remove fragment in run time
            FragmentManager fragmentManager = getSupportFragmentManager();

            //Create and display Heads Body Fragment
            BodyPartFragment headFragment = new BodyPartFragment();
            headFragment.setImageIds(AndroidImageAssets.getHeads());
            int headIndex = information.getInt("headIndex",0);
            headFragment.setListIndex(headIndex);
            fragmentManager.beginTransaction()
                    .add(R.id.head_container,headFragment)
                    .commit();

            //Create and display Body Fragment
            BodyPartFragment bodyFragment = new BodyPartFragment();
            bodyFragment.setImageIds(AndroidImageAssets.getBodies());
            int bodyIndex = information.getInt("bodyIndex",0);
            bodyFragment.setListIndex(bodyIndex);
            fragmentManager.beginTransaction()
                    .add(R.id.body_container,bodyFragment)
                    .commit();

            //Create and display Legs Body Fragment
            BodyPartFragment legsFragment = new BodyPartFragment();
            legsFragment.setImageIds(AndroidImageAssets.getLegs());
            int legIndex = information.getInt("legIndex",0);
            legsFragment.setListIndex(legIndex);
            fragmentManager.beginTransaction()
                    .add(R.id.legs_container,legsFragment)
                    .commit();
        }
    }
}
