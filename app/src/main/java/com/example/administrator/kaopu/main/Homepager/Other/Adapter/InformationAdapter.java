package com.example.administrator.kaopu.main.Homepager.Other.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.kaopu.R;
import com.example.administrator.kaopu.main.Homepager.Other.Bean.InformationBean;
import com.example.administrator.kaopu.main.Homepager.This.HomePageAdapter;
import com.example.administrator.kaopu.main.Homepager.This.HomePagerBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class InformationAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<InformationBean> list;
    public InformationAdapter(Context mContext,List<InformationBean> list){
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
    public void reloadGridView(List<InformationBean> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder=null;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.information_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(list.get(position).getImg().get(0)).into(mHolder.image_infoHouse);
        mHolder.text_title.setText(list.get(position).getTitle());
        mHolder.textView_info.setText(list.get(position).getUpinfo());
        mHolder.text_date.setText(list.get(position).getPurDate());
        return convertView;
    }
    class ViewHolder{
        private TextView text_title;
        private ImageView image_infoHouse;
        private TextView textView_info;
        private TextView text_date;
        public ViewHolder(View convertView){
            text_title= (TextView) convertView.findViewById(R.id.text_title);
            image_infoHouse= (ImageView) convertView.findViewById(R.id.image_infoHouse);
            textView_info= (TextView) convertView.findViewById(R.id.textView_info);
            text_date= (TextView) convertView.findViewById(R.id.text_date);
        }
    }
}
