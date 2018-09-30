package com.example.administrator.kaopu.main.Shop.This;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.kaopu.R;
import com.example.administrator.kaopu.main.Homepager.Other.Adapter.GoldKeeperAdapter;
import com.example.administrator.kaopu.main.Homepager.Other.Bean.GoldKeeperBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class StoreAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<StoreBean> list;
    public StoreAdapter(Context mContext,List<StoreBean> list){
        this.mContext=mContext;
        this.list=list;
        mInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder=null;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.store_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        mHolder.text_stores.setText(list.get(position).getStores());
        mHolder.text_area.setText("["+list.get(position).getArea()+"]");
        mHolder.text_address.setText(list.get(position).getAddress());
        mHolder.text_phone.setText(list.get(position).getPhone());
        return convertView;
    }
    public void reloadGridView(List<StoreBean> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
    class ViewHolder{
        private TextView text_stores;
        private TextView text_area;
        private TextView text_address;
        private TextView text_phone;
        public ViewHolder(View convertView){
            text_stores= (TextView) convertView.findViewById(R.id.text_stores);
            text_area= (TextView) convertView.findViewById(R.id.text_area);
            text_address= (TextView) convertView.findViewById(R.id.text_address);
            text_phone= (TextView) convertView.findViewById(R.id.text_phone);

        }
    }
}
