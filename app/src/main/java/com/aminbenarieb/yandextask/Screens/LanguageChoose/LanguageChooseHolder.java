package com.aminbenarieb.yandextask.Screens.LanguageChoose;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.aminbenarieb.yandextask.R;

/**
 * Created by aminbenarieb on 4/9/17.
 */

public class LanguageChooseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Button mButtonLanguage;
    Context mContext;

    LanguageChooseHolder(View itemView, Context context) {
        super(itemView);
        this.mContext = context;
        this.mButtonLanguage = (Button) itemView.findViewById(R.id.button_language);
    }

    public Button getButtonLanguage() {
        return mButtonLanguage;
    }

    void setOnClickListener() {
        mButtonLanguage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(!(mContext instanceof LanguageChooseActivity)){
            return;
        }

        LanguageChooseActivity activity = (LanguageChooseActivity)mContext;
        Intent intent = new Intent();
        intent.putExtra("LANGUAGE", mButtonLanguage.getText());
        activity.setResult(activity.RESULT_OK, intent);
        activity.finish();
    }
}
