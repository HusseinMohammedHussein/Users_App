package com.e.usersdata.ui.activity.usersActivity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.e.usersdata.data.model.UsersModel;

import java.util.List;

public class UsersViewModel extends ViewModel {

    private final UsersRepository usersRepository;

    public UsersViewModel() {
        usersRepository = new UsersRepository();
    }

    public MutableLiveData<List<UsersModel>> usersMutableLiveData() {
        return usersRepository.getUsers();
    }

}
