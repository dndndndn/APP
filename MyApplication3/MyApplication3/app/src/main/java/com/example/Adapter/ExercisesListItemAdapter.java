package com.example.Adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import com.example.myapplication.ExercisesActivity;
import com.example.Models.Groups;
import com.example.myapplication.R;


public class ExercisesListItemAdapter extends BaseAdapter {

    private List<Groups> objects = new ArrayList<Groups>();
    private Context context;
    private LayoutInflater layoutInflater;

    public ExercisesListItemAdapter(Context context){
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
    }
    /**
     * 设置数据更新界面
     */
    public void setData(List<Groups> objects){
        this.objects = objects;
        notifyDataSetChanged();
    }
    //
    public void updateView(List<Groups> objects){
        this.objects = objects;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return objects == null ? 0 : objects.size();
    }

    /**
     * 根据position得到对应的Item的对象
     */
    @Override
    public Groups getItem(int position) {
        return objects.get(position);
    }

    /**
     * 根据position得到对应Item的对象
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 得到相应position对应的Item视图，position是当前Item的位置
     * convertView参数就是滚出屏幕的Item的View
     * 第一次进入或滑动屏幕时候被调用
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//convertview 缓存界面VIEW免得每次都加在xml
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.group_list_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews( position,(ViewHolder) convertView.getTag()
                , convertView);
        return convertView;
    }

    private void initializeViews(int position, ViewHolder holder, View convertView) {
        final Groups bean = getItem(position);
        if (bean != null) {
            holder.tvOrder.setText(position + 1 + "");//GET.DATA(FDDDF)
            holder.tvTitle.setText(bean.title);
            holder.tvContent.setText("共计"+bean.count+"题");
            holder.tvOrder.setBackgroundResource(bean.background);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到习题分类界面
                    Intent intent=new Intent(context, ExercisesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("group",bean.id);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }

    protected class ViewHolder {
        private TextView tvOrder;
        private TextView tvTitle;
        private TextView tvContent;

        public ViewHolder(View view) {
            tvOrder = (TextView) view.findViewById(R.id.tv_order);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
        }
    }
}
