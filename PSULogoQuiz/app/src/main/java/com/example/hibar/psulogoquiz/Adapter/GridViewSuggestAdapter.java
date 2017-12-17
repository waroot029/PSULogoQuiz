package com.example.hibar.psulogoquiz.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.List;

import com.example.hibar.psulogoquiz.Common.Common;
import com.example.hibar.psulogoquiz.MainActivity2;

/**
 * Created by reale on 3/9/2017.
 */

public class GridViewSuggestAdapter extends BaseAdapter {

    private List<String> suggestSource;
    private Context context;
    private MainActivity2 mainActivity2;

    public GridViewSuggestAdapter(List<String> suggestSource, Context context, MainActivity2 mainActivity2) {
        this.suggestSource = suggestSource;
        this.context = context;
        this.mainActivity2 = mainActivity2;
    }

    @Override
    public int getCount() {
        return suggestSource.size();
    }

    @Override
    public Object getItem(int position) {
        return suggestSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Button button;
        if(convertView == null)
        {
            if(suggestSource.get(position).equals("null"))
            {
                button = new Button(context);
                button.setLayoutParams(new GridView.LayoutParams(85,85));
                button.setPadding(8,8,8,8);
                button.setBackgroundColor(Color.DKGRAY);
            }
            else
            {
                button = new Button(context);
                button.setLayoutParams(new GridView.LayoutParams(85,85));
                button.setPadding(8,8,8,8);
                button.setBackgroundColor(Color.DKGRAY);
                button.setTextColor(Color.YELLOW);
                button.setText(suggestSource.get(position));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //If correct answer contains character user selected
                        if(String.valueOf(mainActivity2.answer).contains(suggestSource.get(position)))
                        {
                            char compare = suggestSource.get(position).charAt(0); // Get char

                            for(int i =0;i<mainActivity2.answer.length;i++)
                            {
                                if(compare == mainActivity2.answer[i])
                                    Common.user_submit_answer[i] = compare;
                            }

                            //Update UI
                            GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(Common.user_submit_answer,context);
                            mainActivity2.gridViewAnswer.setAdapter(answerAdapter);
                            answerAdapter.notifyDataSetChanged();

                            //Remove from suggest source
                            mainActivity2.suggestSource.set(position,"null");
                            mainActivity2.suggestAdapter = new GridViewSuggestAdapter(mainActivity2.suggestSource,context,mainActivity2);
                            mainActivity2.gridViewSuggest.setAdapter(mainActivity2.suggestAdapter);
                            mainActivity2.suggestAdapter.notifyDataSetChanged();
                        }
                        else // else
                        {
                            //Remove from suggest source
                            mainActivity2.suggestSource.set(position,"null");
                            mainActivity2.suggestAdapter = new GridViewSuggestAdapter(mainActivity2.suggestSource,context,mainActivity2);
                            mainActivity2.gridViewSuggest.setAdapter(mainActivity2.suggestAdapter);
                            mainActivity2.suggestAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }
        else
            button = (Button)convertView;
        return button;

    }
}
