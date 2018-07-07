package com.example.android.todolist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.todolist.database.AppDatabase;

/**
 * Created by AmrDeveloper on 7/7/2018.
 */

public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {

   private final AppDatabase mDatabase;
   private final int taskId;

    public AddTaskViewModelFactory(AppDatabase mDatabase, int taskId) {
        this.mDatabase = mDatabase;
        this.taskId = taskId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        return (T) new AddTaskViewModel(mDatabase,taskId);

    }
}
