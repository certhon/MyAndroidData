package com.thestore.main.app.mystore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thestore.main.app.mystore.R;
import com.thestore.main.core.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class SelectorView extends LinearLayout {
    private Context mContext;
    private View spetalLine;
    private List<View> childViews;
    private OnItemChangeListener onItemChangeListener;



    public SelectorView(Context context) {
        super(context);
        initView(context);
    }

    public SelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SelectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
        childViews = new ArrayList<>();
    }

    public void bindData(List<String> items){
        removeAllViews();
        childViews.clear();
        for (int i=0;i<items.size();i++){
            addItem(items.get(i),i==(items.size()-1),i);
        }
        setCurrentItem(0);
    }

    private void addItem(String name, boolean isLast,int i) {
        View itemView =  LayoutInflater.from(mContext).inflate(R.layout.selector_item_view, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.itemNameTV = (TextView) itemView.findViewById(R.id.item_name_tv);
        viewHolder.itemLine = itemView.findViewById(R.id.item_line);
        viewHolder.countNumTV = (TextView) itemView.findViewById(R.id.count_num_tv);
        viewHolder.id = i;
        itemView.setTag(viewHolder);


        LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        itemView.setLayoutParams(layoutParams);
        viewHolder.itemNameTV.setText(name);

        itemView.setOnClickListener(onItemClickListener);

        addView(itemView);
        childViews.add(itemView);
        if (!isLast){
            addSpetalLine();
        }
    }

    private void addSpetalLine() {
        spetalLine = new View(mContext);
        LinearLayout.LayoutParams  layoutParams = new LayoutParams(DensityUtil.dip2px(mContext,1),DensityUtil.dip2px(mContext,20));
        spetalLine.setLayoutParams(layoutParams);
        spetalLine.setBackgroundColor(mContext.getResources().getColor(R.color.gray_ebebeb));
        addView(spetalLine);
    }

    private OnClickListener onItemClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            ViewHolder viewHolder = (ViewHolder) v.getTag();
            setCurrentItem(viewHolder.id);
            if (onItemChangeListener!=null){
                onItemChangeListener.onItemChange(viewHolder.id);
            }
        }
    };


    public void setCurrentItem(int currentItem){
        ViewHolder holder;
        for (int i = 0 ; i < childViews.size() ; i++){
            holder = (ViewHolder) childViews.get(i).getTag();
            if (currentItem == i){
                holder.itemNameTV.setTextColor(mContext.getResources().getColor(R.color.red_ff5559));
                holder.itemLine.setVisibility(VISIBLE);
            } else {
                holder.itemNameTV.setTextColor(mContext.getResources().getColor(R.color.gray_757575));
                holder.itemLine.setVisibility(INVISIBLE);
            }
        }
    }

    public void setCountNum(int[] counts){
        ViewHolder holder;
        for (int i = 0 ; i < counts.length ; i++){
            holder = (ViewHolder) childViews.get(i).getTag();
            bindCountNum(holder.countNumTV,counts[i]);
        }
    }

    private void bindCountNum(TextView countNumTV, int count) {
        if (count == 0){
            countNumTV.setVisibility(View.GONE);
        } else if (count >99){
            countNumTV.setVisibility(View.VISIBLE);
            countNumTV.setText("99+");
        } else {
            countNumTV.setVisibility(View.VISIBLE);
            countNumTV.setText(count+"");
        }
    }

    class ViewHolder{
        int id;
        TextView itemNameTV,countNumTV;
        View itemLine;
    }

    public interface OnItemChangeListener{
       public void onItemChange(int i);
    }

    public void setOnItemChangeListener(OnItemChangeListener listener){
        this.onItemChangeListener = listener;
    }

}
