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
import com.example.administrator.kaopu.main.Homepager.Other.Activity.HotHouseBean;
import com.example.administrator.kaopu.main.Homepager.This.HomePageAdapter;
import com.example.administrator.kaopu.main.Homepager.This.HomePagerBean;
import com.example.administrator.kaopu.main.Recommend.Other.RentingBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class HouseSellAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<HotHouseBean> list;
    public HouseSellAdapter(Context mContext,List<HotHouseBean> list){
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
            convertView=mInflater.inflate(R.layout.housesell_listview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        if (position<=3){
            mHolder.text_type.setText("[二手房]");
        }else {
            mHolder.text_type.setText("[租房]");
        }
        mHolder.text_houseName.setText(list.get(position).getName());
        mHolder.textView_houseAdress.setText(list.get(position).getAddress());
        mHolder.text_Houseprice.setText(list.get(position).getPrice());
        Glide.with(mContext).load(list.get(position).getImg().get(0)).into(mHolder.image_house);
        String fitment = list.get(position).getFitment();
        if(fitment.contains("、")){
            mHolder.text_secondOnSale.setVisibility(View.VISIBLE);
            String result = fitment.substring(0, fitment.indexOf("、"));
            mHolder.text_onSale.setText(result);
            String two=fitment.substring(fitment.indexOf("、")+1,fitment.length());
            mHolder.text_secondOnSale.setText(two);
        }else {
            mHolder.text_secondOnSale.setVisibility(View.INVISIBLE);
            mHolder.text_onSale.setText(list.get(position).getFitment());
        }
        return convertView;
    }
    public void reloadGridView(List<HotHouseBean> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
    class ViewHolder{
        private TextView text_houseName;
        private ImageView image_house;
        private TextView textView_houseAdress;
        private TextView text_Houseprice;
        private TextView text_onSale;
        private TextView text_secondOnSale;
        private TextView text_type;
        public ViewHolder(View convertView){
            text_type= (TextView) convertView.findViewById(R.id.text_type);
            text_secondOnSale= (TextView) convertView.findViewById(R.id.text_secondOnSale);
            text_onSale= (TextView) convertView.findViewById(R.id.text_onSale);
            text_houseName= (TextView) convertView.findViewById(R.id.text_houseName);
            image_house= (ImageView) convertView.findViewById(R.id.image_house);
            textView_houseAdress= (TextView) convertView.findViewById(R.id.textView_houseAdress);
            text_Houseprice= (TextView) convertView.findViewById(R.id.text_Houseprice);
        }
    }
}
