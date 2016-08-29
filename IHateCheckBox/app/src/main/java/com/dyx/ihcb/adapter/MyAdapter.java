package com.dyx.ihcb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyx.ihcb.R;
import com.dyx.ihcb.model.ModelRoot;

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


    public void setOnCbClickListener(MyAdapter.OnCbClickListener onCbClickListener) {
        OnCbClickListener = onCbClickListener;
    }

    public interface OnCbClickListener {
        void onItemClick(int index, boolean isSelcted);
    }

    public MyAdapter(Context mContext, List<ModelRoot.DataBean.GroupsBean.GoodsListBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_optional_detail_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvGoodsName.setText(mDatas.get(position).getGoodsName());
        holder.cbGoodsSelected.setTag(position);
        holder.cbGoodsSelected.setChecked(mDatas.get(position).isSelected());
        holder.cbGoodsSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (OnCbClickListener != null) {
                    int pos = (int) compoundButton.getTag();
                    OnCbClickListener.onItemClick(pos, b);
                }
            }
        });
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
