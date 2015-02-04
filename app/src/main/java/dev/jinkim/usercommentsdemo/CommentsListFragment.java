package dev.jinkim.usercommentsdemo;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Jin on 2/4/15.
 */
public class CommentsListFragment extends ListFragment {


    public static final String TAG = "CommentsListFragment";
    private CommentsAdapter adapter;
    private ListView lvComments;
    private Singleton app;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_comments_list, container, false);
        app = Singleton.getInstance();

        adapter = new CommentsAdapter(getActivity(), new ArrayList<Comment>());
        setListAdapter(adapter);

        retrieveComments();

        return rootView;
    }

    /* Initialize list view after the fragment view is inflated */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initializeViews();
    }

    private void initializeViews() {
        lvComments = getListView();
        lvComments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Comment comment = (Comment) parent.getAdapter().getItem(position);
            }
        });
    }

    /**
     * Make a web call to retrieve comments from the usercomments API.
     * When the results are available (parsed by Gson), list will be updated
     */
    private void retrieveComments() {
        RestClient.ApiService rest = new RestClient().getApiService();
        rest.listComments(app.getSessionName() + "=" + app.getSessionId(), new Callback<List<Comment>>() {
            @Override
            public void success(List<Comment> comments, Response response) {
                Log.d(TAG, "Successfully retrieved comments: " + String.valueOf(comments.size()));
                updateList(comments);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Failure in retrieving comments: " + error);
            }
        });
    }

    /**
     * Update the list view UI with the retrieved comments
     * @param comments
     */
    private void updateList(List<Comment> comments) {
        adapter.clear();
        adapter.addAll(comments);
        adapter.notifyDataSetChanged();
    }
}
