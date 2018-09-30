package com.example.administrator.kaopu.main.Homepager.Other.Adapter;

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
import com.example.administrator.kaopu.main.Homepager.Other.Bean.GoldKeeperBean;
import com.example.administrator.kaopu.main.Homepager.This.HomePagerBean;

import java.util.List;

import static android.R.attr.width;
import static java.security.AccessController.getContext;

/**
 * Created by Administrator on 2017/8/31.
 */

public class GoldKeeperAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<GoldKeeperBean> list;
    public GoldKeeperAdapter(Context mContext,List<GoldKeeperBean> list){
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
            convertView=mInflater.inflate(R.layout.goldkeeper_gridview_item,parent,false);
            mHolder=new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) mHolder.image_keeper.getLayoutParams();
        //获取当前控件的布局对象
        params.height=2*width/5;//设置当前控件布局的高度
        mHolder.image_keeper.setLayoutParams(params);//将设置好的布局参数应用到控件中
        mHolder.textView_name.setText(list.get(position).getName()+" "+"|"+" "+list.get(position).getStores());
        Glide.with(mContext).load(list.get(position).getImg().get(0)).into(mHolder.image_keeper);
        return convertView;
    }
    public void reloadGridView(List<GoldKeeperBean> _list, boolean isClear) {
        if (isClear) {
            list.clear();
        }
        list.addAll(_list);
        notifyDataSetChanged();
    }
    class ViewHolder{
        private LinearLayout line;
        private TextView textView_name;
        private ImageView image_keeper;
        public ViewHolder(View convertView){
            textView_name= (TextView) convertView.findViewById(R.id.textView_name);
            image_keeper= (ImageView) convertView.findViewById(R.id.image_keeper);
        }
    }
}
