package com.aminbenarieb.yandextask.Screens.LanguageChoose;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aminbenarieb.yandextask.R;

import java.util.List;

/**
 * Created by aminbenarieb on 4/9/17.
 */

public class Adapter extends RecyclerView.Adapter<Holder> {
    private Context mContext;
    List<String> mLanguageList;

    public Adapter(List<String> mLanguageList,Context context){
        this.mContext = context;
        this.mLanguageList = mLanguageList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.language_row,
                parent,
                false);
        return new Holder(view, mContext);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.getButtonLanguage().setText(mLanguageList.get(position));
        holder.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return mLanguageList.size();
    }
}
