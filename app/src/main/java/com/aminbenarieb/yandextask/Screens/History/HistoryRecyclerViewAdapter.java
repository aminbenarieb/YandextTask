/*
* Copyright (C) 2014 The Android Open Source Project
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

package com.aminbenarieb.yandextask.Screens.History;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aminbenarieb.yandextask.Entity.Word.*;
import com.aminbenarieb.yandextask.R;

import java.util.List;


public class HistoryRecyclerViewAdapter
        extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>
        implements  HistoryViewHolderDelegate {
    private static final String TAG = "HRViewAdapter";
    private List<WordInfo> mDataSet;
    private HistoryAdapterDelegate delegate;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtViewSource;
        private final TextView txtViewResult;
        private final ImageButton btnBookmark;
        private  HistoryViewHolderDelegate delegate;

        private final View.OnClickListener btnAddBookmarkListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.didTapBookmarkOnRow( getAdapterPosition() );

            }
        };
        private final View.OnClickListener btnHistoryListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.didTapOnRow( getAdapterPosition() );

            }
        };
        public ViewHolder(View v, HistoryViewHolderDelegate delegate) {
            super(v);

            this.delegate = delegate;

            txtViewSource = (TextView) v.findViewById(R.id.text_source);
            txtViewResult = (TextView) v.findViewById(R.id.text_result);
            btnBookmark = (ImageButton) v.findViewById(R.id.add_to_bookmarks);

            v.setOnClickListener( btnHistoryListener );
            btnBookmark.setOnClickListener( btnAddBookmarkListener );

        }

        public TextView getTextViewSource() {
            return txtViewSource;
        }
        public TextView getTextViewResult() {
            return txtViewResult;
        }
        public ImageButton getButtonBookmark() {
            return btnBookmark;
        }
    }

    //region Constructor
    public HistoryRecyclerViewAdapter(List<WordInfo> dataSet, HistoryAdapterDelegate delegate) {
        mDataSet = dataSet;
        this.delegate = delegate;
    }
    //endregion

    // region Adapter subroutines
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bookmark_row, viewGroup, false);

        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        WordInfo word = mDataSet.get(position);
        viewHolder.getTextViewSource().setText(word.getSource());
        viewHolder.getTextViewResult().setText(word.getResult());

        int bookmarkIcon = word.getFavorite() ? R.drawable.bookmark : R.drawable.bookmark_border;
        viewHolder.getButtonBookmark().setImageResource( bookmarkIcon );

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    //endregion

    //region DataSet

    public void updateDataset(List<WordInfo> dataset) {
        mDataSet.clear();
        mDataSet.addAll( dataset );
        this.notifyDataSetChanged();
    }

    //endregion

    //region View Holder Events
    @Override
    public void didTapOnRow(int row) {
        WordInfo word = mDataSet.get(row);
        delegate.didTapOnWord(word);
    }
    @Override
    public void didTapBookmarkOnRow(int row) {
        WordInfo wordInfo = mDataSet.get(row);
        wordInfo.setFavorite( !wordInfo.getFavorite() );
        this.notifyItemChanged(row);
        delegate.didToggleWordFavorite( wordInfo );
    }
    @Override
    public void didSwipeDeleteOnRow(int row) {
        WordInfo word = mDataSet.get(row);
        delegate.didDeleteWord(word);
    }
    //endregion


}
