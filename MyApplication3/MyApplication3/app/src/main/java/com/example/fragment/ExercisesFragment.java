package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.example.Adapter.ExercisesListItemAdapter;
import com.example.Models.Groups;
import com.example.myapplication.R;


public class ExercisesFragment extends Fragment {

    private ListView lvList;//来源fragment_exercises
    private ExercisesListItemAdapter adapter; //适配器
    private List<Groups> ebl; //列表集合

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercises, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        lvList = view.findViewById(R.id.lv_list);
        adapter = new ExercisesListItemAdapter(getActivity());
        adapter.setData(ebl);
        lvList.setAdapter(adapter);
    }

    private void initData(){
        ebl = new ArrayList<Groups>();
        for (int i=0;i<10;i++){
            Groups bean = new Groups();
            bean.id=(i+1);
            switch (i){
                case 0:
                    bean.title="第一章 电路模型和电路定律";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 1:
                    bean.title="第二章 电阻电路的等效变换";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_2);
                    break;
                case 2:
                    bean.title="第三章 电阻电路的一般分析";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_3);
                    break;
                case 3:
                    bean.title="第四章 电路定理";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_4);
                    break;
                case 4:
                    bean.title="第六章 储能元件";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 5:
                    bean.title="第七章 一阶电路和二阶电路的时域分析";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_2);
                    break;
                case 6:
                    bean.title="第八章 相量法";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_3);
                    break;
                case 7:
                    bean.title="第九章 正弦稳态电路的分析";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_4);
                    break;
                case 8:
                    bean.title="第十章 含有耦合电感的电路";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 9:
                    bean.title="第十一章 电路的频率响应";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_2);
                    break;
                default:
                    break;
            }
            ebl.add(bean);
        }
    }
}