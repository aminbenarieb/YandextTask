package com.aminbenarieb.yandextask.Screens.History;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.aminbenarieb.yandextask.Entity.Word.*;
import com.aminbenarieb.yandextask.R;
import com.aminbenarieb.yandextask.Services.Repository.ABRepository;
import com.aminbenarieb.yandextask.Services.Repository.ABRepositoryRequest;
import com.aminbenarieb.yandextask.Services.Repository.ABRepositoryResponse;
import com.aminbenarieb.yandextask.Services.Repository.Repository;
import com.aminbenarieb.yandextask.Services.Repository.RepositoryResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by aminbenarieb on 4/7/17.
 */

public class HistoryListFragment extends Fragment implements HistoryAdapterDelegate {
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RecyclerView mRecyclerView;
    protected HistoryRecyclerViewAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private Repository mRepository;

    //region Fragment

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = new ABRepository(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_history_recyclerview, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new HistoryRecyclerViewAdapter(new ArrayList<WordInfo>(), this);
        mRecyclerView.setAdapter(mAdapter);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        showHistory();

        // Swipes
        mAdapter.getTouchHelper().attachToRecyclerView(mRecyclerView);

        return rootView;
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    //endregion

    //region HistoryAdapterDelegate

    @Override
    public void didTapOnWord(WordInfo word) {

    }

    @Override
    public void didDeleteWord(WordInfo word) {
        mRepository.removeWord(new ABRepositoryRequest(word),
                new Repository.RepositoryCompletionHandler() {
                    @Override
                    public void handle(RepositoryResponse response) {

                    }
                });
    }

    @Override
    public void didToggleWordFavorite(WordInfo word) {
        mRepository.toggleFavoriteWord(new ABRepositoryRequest(word),
                new Repository.RepositoryCompletionHandler() {
                    @Override
                    public void handle(RepositoryResponse response) {
                        Throwable t = ((ABRepositoryResponse)response).getError();
                        if (t != null) {
                            Toast.makeText(getActivity(),
                                    t.getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void didClearHistory() {

    }

    //endregion

    //region HistoryView

    public void showHistory() {
        this.mRepository.getHistoryWords(new Repository.RepositoryCompletionHandler() {
            @Override
            public void handle(RepositoryResponse response) {
                List<WordInfo> mDataset = ((ABRepositoryResponse)response).getWords();
                mAdapter.updateDataset(mDataset);
            }
        });
    }

    public void showBookmarks() {
        this.mRepository.getFavoriteHistoryWords(new Repository.RepositoryCompletionHandler() {
            @Override
            public void handle(RepositoryResponse response) {
                List<WordInfo> mDataset = ((ABRepositoryResponse)response).getWords();
                mAdapter.updateDataset(mDataset);
            }
        });
    }

    //endregion

}
