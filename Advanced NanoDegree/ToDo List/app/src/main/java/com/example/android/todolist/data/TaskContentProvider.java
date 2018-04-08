/*
* Copyright (C) 2016 The Android Open Source Project
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

package com.example.android.todolist.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

public class TaskContentProvider extends ContentProvider {

    //Two integer number for Matcher
    public static final int TASKS = 100;
    public static final int TASKS_WITH_ID = 101;

    //Static Matcher
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        //<Authority> <Path> <Integer>
        //directory
        matcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASK, TASKS);
        //Single Item
        matcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASK + "/#", TASKS_WITH_ID);
        return matcher;
    }

    private TaskDbHelper mTaskDbHelper;

    /* onCreate() is where you should initialize anything you’ll need to setup
    your underlying data source.
    In this case, you’re working with a SQLite database, so you’ll need to
    initialize a DbHelper to gain access to it.
     */
    @Override
    public boolean onCreate() {
        // Declare the DbHelper as a global variable
        Context context = getContext();
        //initialize a TaskDbhelper on startup
        mTaskDbHelper = new TaskDbHelper(context);
        return true;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase database = mTaskDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case TASKS:
                long id = database.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    //Insert Success
                    returnUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row in database " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
        //Notify Changed in database
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase database = mTaskDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor cursor;
        switch (match) {
            case TASKS:
                cursor = database.query(
                        TaskContract.TaskEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            // Add a case to query for a single row of data by ID
            // Use selections and selectionArgs to filter for that ID
            case TASKS_WITH_ID:
                // Get the id from the URI
                String id = uri.getPathSegments().get(1);

                // Selection is the _ID column = ?, and the Selection args = the row ID from the URI
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};

                // Construct a query as you would normally, passing in the selection/args
                cursor = database.query(
                        TaskContract.TaskEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new android.database.SQLException("Failed to get Query from database");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return cursor;
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        final SQLiteDatabase database = mTaskDbHelper.getWritableDatabase();
        int state;
        switch (match) {
            case TASKS:
                state =  database.delete(
                        TaskContract.TaskEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case TASKS_WITH_ID:
                String id = uri.getPathSegments().get(1);
                selection = "_id=?";
                selectionArgs = new String[]{id};
                state = database.delete(
                        TaskContract.TaskEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return state;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        //Keep track of if an update occurs
        int tasksUpdated;

        // match code
        int match = sUriMatcher.match(uri);

        switch (match) {
            case TASKS_WITH_ID:
                //update a single task by getting the id
                String id = uri.getPathSegments().get(1);
                //using selections
                tasksUpdated = mTaskDbHelper.getWritableDatabase().update(
                        TaskContract.TaskEntry.TABLE_NAME,
                        values,
                        "_id=?",
                        new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksUpdated != 0) {
            //set notifications if a task was updated
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // return number of tasks updated
        return tasksUpdated;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);

        switch (match) {
            case TASKS:
                // directory
                return "vnd.android.cursor.dir" + "/" + TaskContract.AUTHORITY + "/" + TaskContract.PATH_TASK;
            case TASKS_WITH_ID:
                // single item type
                return "vnd.android.cursor.item" + "/" + TaskContract.AUTHORITY + "/" + TaskContract.PATH_TASK;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

}
