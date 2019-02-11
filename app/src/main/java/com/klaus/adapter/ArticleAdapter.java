package com.klaus.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.klaus.bean.WXarticle;

import java.util.List;

public class ArticleAdapter extends BaseAdapter {

    private final Context mContext;
    private List<WXarticle.DataBean> mData;

    public ArticleAdapter(Context context, List<WXarticle.DataBean> datas) {
        this.mContext = context;
        this.mData = datas;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(mContext, android.R.layout.simple_list_item_1, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(mData.get(i).getName());

        return view;
    }

    static class ViewHolder {
        public TextView textView;

        public ViewHolder(View view) {
            textView = view.findViewById(android.R.id.text1);
        }
    }
}
