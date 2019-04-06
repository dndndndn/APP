package com.example.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.myapplication.ExercisesBean;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ChoiceListAdapter extends BaseAdapter {
    private List<Bitmap> objects = new ArrayList<Bitmap>();
    private Context context;
    private LayoutInflater layoutInflater;

    public ChoiceListAdapter(Context context){
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
    }
    /**
     * 设置数据更新界面
     */
    public void setData(List<Bitmap> objects){
        this.objects = objects;
        notifyDataSetChanged();
    }
    //
    public void updateView(List<Bitmap> objects){
        this.objects = objects;
        this.notifyDataSetChanged();
    }

    public int getCount() {
        return objects == null ? 0 : objects.size();
    }
    /**
     * 根据position得到对应Item的对象
     */

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Bitmap getItem(int position){return objects.get(position);}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//convertview 缓存界面VIEW免得每次都加在xml
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.choice_list_item, null);
            convertView.setTag(new ChoiceListAdapter.ViewHolder(convertView));
        }
        initializeViews( position,(ChoiceListAdapter.ViewHolder) convertView.getTag()
                , convertView);
        return convertView;
    }

    private void initializeViews(int position, ChoiceListAdapter.ViewHolder holder, View convertView) {
        Bitmap bean=getItem(position);
        if (bean!= null) {
            holder.checkBox.setChecked(false);
            holder.imageView.setImageBitmap(bean);
        }
    }

    protected class ViewHolder {
        private CheckBox checkBox;
        private ImageView imageView;

        public ViewHolder(View view) {
            checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            imageView = (ImageView) view.findViewById(R.id.choice);
        }
    }
}
