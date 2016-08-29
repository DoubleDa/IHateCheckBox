package com.dyx.ihcb;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dyx.ihcb.adapter.MyAdapter;
import com.dyx.ihcb.model.ModelRoot;
import com.dyx.ihcb.utils.AssetsUtils;
import com.dyx.ihcb.utils.JsonHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    @Bind(R.id.ll_root)
    LinearLayout llRoot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        try {
            String jsonData = new String(AssetsUtils.InputStreamToByte(getClass().getResourceAsStream("/assets/my.json")));
            ModelRoot modelRoot = JsonHelper.fromJson(jsonData, ModelRoot.class);
            List<ModelRoot.DataBean.GroupsBean> groupsBeen = modelRoot.getData().getGroups();
            int itemNum = groupsBeen.size();
            if (groupsBeen != null && groupsBeen.size() > 0) {
                for (int i = 0; i < itemNum; i++) {
                    View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_optional_layout, null);
                    RecyclerView mRecycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
                    TextView textView = (TextView) view.findViewById(R.id.tv_group_title);

                    LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    mRecycler_view.setLayoutManager(manager);

                    MyAdapter adapter = new MyAdapter(MainActivity.this, groupsBeen.get(i).getGoodsList());
                    mRecycler_view.setAdapter(adapter);
                    adapter.setOnCbClickListener(new MyAdapter.OnCbClickListener() {
                        @Override
                        public void onItemClick(int index, boolean isSelcted) {
                            if (isSelcted) {
                                Toast.makeText(MainActivity.this, "选中", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    llRoot.addView(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
