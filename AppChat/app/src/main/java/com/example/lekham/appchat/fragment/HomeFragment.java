package com.example.lekham.appchat.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lekham.appchat.R;
import com.example.lekham.appchat.activity.ChatActivity;
import com.example.lekham.appchat.adapter.UserListingRecyclerAdapter;
import com.example.lekham.appchat.core.users.get.GetUserContract;
import com.example.lekham.appchat.core.users.get.GetUserPresenter;
import com.example.lekham.appchat.models.User;
import com.example.lekham.appchat.utils.ItemClickSupport;
import com.example.lekham.appchat.utils.Toaster;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, GetUserContract.View, ItemClickSupport.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private RecyclerView mRecyclerViewAllUserListing;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View view;
    private GetUserPresenter mGetUserPresenter;
    private List<User> userList = new ArrayList<>();
    private UserListingRecyclerAdapter mUserListingRecyclerAdapter;

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return view;
    }

    private void init() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerViewAllUserListing = (RecyclerView) view.findViewById(R.id.recycler_view_all_user_listing);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mGetUserPresenter = new GetUserPresenter(this);
        handelGetAllUser();
        handelSetRefreshing(true);
        ItemClickSupport.addTo(mRecyclerViewAllUserListing).setOnItemClickListener(this);
    }

    @Override
    public void onRefresh() {
        handelGetAllUser();
    }

    @Override
    public void onGetAllUsersSuccess(List<User> users) {
        handelSetRefreshing(false);
        if (users != null && users.size() > 0) {
            userList = users;
            mUserListingRecyclerAdapter = new UserListingRecyclerAdapter(users);
            mRecyclerViewAllUserListing.setAdapter(mUserListingRecyclerAdapter);
            mUserListingRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetAllUsersFailure(String message) {
        Toaster.error(getActivity(), message.toString(), Toast.LENGTH_SHORT).show();
        handelSetRefreshing(false);
    }

    @Override
    public void onGetChatUsersSuccess(List<User> users) {

    }

    @Override
    public void onGetChatUsersFailure(String message) {

    }

    private void handelGetAllUser() {
        mGetUserPresenter.getAllUsers();
    }

    private void handelSetRefreshing(final boolean value) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(value);
            }
        });
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        if (userList != null && userList.size() > 0) {
            User user = userList.get(position);
            if (user != null) {
                ChatActivity.startIntent(getContext(), user.getEmail(), user.getUid(), user.getFirebaseToken());
            }
        }
    }
}
