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

import android.util.Log;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aminbenarieb.yandextask.Entity.Word.*;
import com.aminbenarieb.yandextask.R;

import java.util.List;


public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "HRViewAdapter";
    private List<Word> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtViewSource;
        private final TextView txtViewResult;

        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            txtViewSource = (TextView) v.findViewById(R.id.text_source);
            txtViewResult = (TextView) v.findViewById(R.id.text_result);
        }

        public TextView getTextViewSource() {
            return txtViewSource;
        }
        public TextView getTextViewResult() {
            return txtViewResult;
        }
    }

    /**
     * Initialize the dataset of the LanguageChoose.
     *
     * @param dataSet List<Word> containing the data to populate views to be used by RecyclerView.
     */
    public HistoryRecyclerViewAdapter(List<Word> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bookmark_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        Word word = mDataSet.get(position);
        viewHolder.getTextViewSource().setText(word.getSource());
        viewHolder.getTextViewResult().setText(word.getResult());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    //region DataSet

    public void updateDataset(List<Word> dataset) {
        mDataSet.clear();
        mDataSet.addAll( dataset );
        this.notifyDataSetChanged();
    }

    //endregion
}
