package com.e.usersdata.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e.usersdata.R;
import com.e.usersdata.databinding.CardUsersListBinding;
import com.e.usersdata.data.model.UsersModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
    private List<UsersModel> mUsersList;
//    private Context mContext;

    public UsersAdapter() {
        this.mUsersList = new ArrayList<>();
    }

    public void setUsersList(List<UsersModel> setUsersList){
        mUsersList = setUsersList;
    }
    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.card_users_list, parent, false);
        CardUsersListBinding cardUsersListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_users_list, parent, false);
        return new UsersViewHolder(cardUsersListBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        UsersModel usersPosition = mUsersList.get(position);

        String addressStreet = usersPosition.getmAddress().getmStreet();
        String addressCity = usersPosition.getmAddress().getmCity();
        String addressSuit = usersPosition.getmAddress().getmSuite();
        String companyName = usersPosition.getmCompany().getmName();

        holder.binding.tvId.setText("ID: " + usersPosition.getmId());
        holder.binding.tvName.setText("Name: " + usersPosition.getmName());
        holder.binding.tvUsername.setText("Username: " + usersPosition.getmUsername());
        holder.binding.tvEmail.setText("Email: " + usersPosition.getmEmail());
        holder.binding.tvPhone.setText("Phone: " + usersPosition.getmPhone());

        holder.binding.tvAddress.setText("Address: " + addressSuit + ", " + addressStreet + ", " + addressCity + ".");
        holder.binding.tvCompany.setText("Company Name: " + companyName + ".");
/*        holder.binding.tvAddress.setText(
                " - Address: " + "\n" +
                        " Street:   " + usersPosition.getmAddress().getmStreet()     + "\n" +
                        " Suite:    " + usersPosition.getmAddress().getmSuite()      + "\n" +
                        " City:     " + usersPosition.getmAddress().getmCity()        + "\n" +
                " - Geo: " + "\n" +
                        " Lat:  " + usersPosition.getmAddress().getmGeo().getmLat() + "\n" +
                        " Lng:  " + usersPosition.getmAddress().getmGeo().getmLng() + "\n"
        );*/


/*        holder.binding.tvCompanyUsers.setText(
                " - Company: " + "\n"+
                        " Name: "         + usersPosition.getmCompany().getmName()           + "\n"+
                        " Catch Phrase: " + usersPosition.getmCompany().getmCatchPhrase()    +"\n"+
                        " Bs: "           + usersPosition.getmCompany().getmBs()             + "\n"
        );*/
    }

    @Override
    public int getItemCount() {
        return mUsersList != null ?  mUsersList.size() : 0;
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        CardUsersListBinding binding;

        public UsersViewHolder(@NonNull CardUsersListBinding binding) {
            super(binding.getRoot());
            this.binding = DataBindingUtil.bind(itemView);

        }
    }
}
