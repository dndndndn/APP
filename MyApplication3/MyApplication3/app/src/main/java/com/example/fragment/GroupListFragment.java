package com.example.fragment;

import android.os.Bundle;
import android.os.Handler;
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
import com.example.utils.GetData;

public class GroupListFragment extends Fragment {

    private ListView lvList;//来源fragment_exercises
    private ExercisesListItemAdapter adapter; //适配器
    private List<Groups> ebl; //列表集合
    private GetData getData;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    run(msg.getData());
                    break;
                case 0x002:
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercises, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvList = view.findViewById(R.id.lv_list);
        adapter = new ExercisesListItemAdapter(getActivity());
        getData=new GetData(this.getActivity(), handler);
        getData.getGroups(0x01);
    }

    private void run(Bundle bundle){
        ebl=new ArrayList<Groups>();
        if(bundle.getString("Length")==null)
            return;
        int Length=Integer.parseInt(bundle.getString("Length"));
        for(int i=0;i<Length;i++){
            Groups group=new Groups();
            group.id=Integer.parseInt(bundle.getString("id"+i));
            group.title=bundle.getString("title"+i);
            group.count=Integer.parseInt(bundle.getString("count"+i));
            group.finished=Integer.parseInt(bundle.getString("finished"+i));
            group.url=bundle.getString("url"+i);
            ebl.add(group);
        }
        adapter.setData(ebl);
        lvList.setAdapter(adapter);
    }
}