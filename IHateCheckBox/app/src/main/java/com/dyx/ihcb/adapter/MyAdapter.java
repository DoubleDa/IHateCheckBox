package com.dyx.ihcb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dyx.ihcb.MainActivity;
import com.dyx.ihcb.R;
import com.dyx.ihcb.model.ModelRoot;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dayongxin on 2016/8/29.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context mContext;
    private List<ModelRoot.DataBean.GroupsBean.GoodsListBean> mDatas;
    private OnCbClickListener OnCbClickListener;
    public SparseArray<Boolean> ischeckArray;
    public SparseArray<CheckBox> listbox;
    private boolean selectedState = false;


    public void setOnCbClickListener(MyAdapter.OnCbClickListener onCbClickListener) {
        OnCbClickListener = onCbClickListener;
    }

    public interface OnCbClickListener {
        void onItemClick(int index);
    }

    public MyAdapter(Context mContext, List<ModelRoot.DataBean.GroupsBean.GoodsListBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        ischeckArray = new SparseArray<>();
        listbox = new SparseArray<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_optional_detail_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        /**
         * llRoot
         */
        holder.llRoot.setId(position);
        holder.llRoot.setOnClickListener(onMyCLick);
        /**
         * tvGoodsName
         */
        holder.tvGoodsName.setText(mDatas.get(position).getGoodsName());
        /**
         * cbGoodsSelected
         */
        SetSelectedState(holder.cbGoodsSelected, ischeckArray.get(position, false));
        /**
         * List CheckBox
         */
        listbox.put(position, holder.cbGoodsSelected);
    }

    public boolean isSelectedState() {
        return selectedState;
    }

    public void setSelectedState(boolean selectedState) {
        this.selectedState = selectedState;
    }

    private void SetSelectedState(CheckBox checkBox, Boolean aBoolean) {
        if (isSelectedState()) {
            checkBox.setChecked(aBoolean);
            if (aBoolean) {
                checkBox.setBackgroundResource(R.mipmap.checkbox_check);
            } else {
                checkBox.setBackgroundResource(R.mipmap.checkbox_empty);
            }
        }
    }

    public View.OnClickListener onMyCLick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = view.getId();
            setSelectedState(true);
            if (ischeckArray == null) {
                ischeckArray.put(position, true);
            } else {
                if (!ischeckArray.get(position, false)) {
                    ischeckArray.put(position, true);
                    addCheckArray();
                } else {
                    ischeckArray.put(position, false);
                    deleteCheckArray();
                }
            }
            SetSelectedState(listbox.get(position), ischeckArray.get(position));
            dataChanged(position);
        }
    };

    private void dataChanged(int position) {
        notifyItemChanged(position);
    }


    /**
     * 删除处理函数
     */
    private void deleteCheckArray() {

    }

    /**
     * 添加处理函数
     */
    private void addCheckArray() {
        int limit = MainActivity.LIMIT_COUNT;
        int size = ischeckArray.size();
        int count = -1;
        for (int i = 0; i < size; i++) {
            boolean isCheck = ischeckArray.get(i);
            if (isCheck) {
                count++;
            }
        }
        Logger.d(count);
        if (count == limit) {
            for (int i = 0; i < size; i++) {
                CheckBox checkBox = listbox.get(i);
                if (!checkBox.isChecked()) {
                    ischeckArray.put(i, false);
                    checkBox.setBackgroundResource(R.mipmap.checkbox_empty);
                    Toast.makeText(mContext, "这个不可选哦！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_goods_img)
        ImageView ivGoodsImg;
        @Bind(R.id.tv_goods_name)
        TextView tvGoodsName;
        @Bind(R.id.cb_goods_selected)
        CheckBox cbGoodsSelected;
        @Bind(R.id.ll_root)
        LinearLayout llRoot;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
