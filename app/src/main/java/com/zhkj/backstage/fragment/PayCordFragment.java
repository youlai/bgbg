package com.zhkj.backstage.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zhkj.backstage.R;
import com.zhkj.backstage.base.BaseLazyFragment;

//支付记录
public class PayCordFragment extends BaseLazyFragment {
    private static final String Param="param";
    private String mParam;
   public static PayCordFragment newInstant(String param){
       PayCordFragment orderdetails=new PayCordFragment();
       Bundle args=new Bundle();
       args.getString(Param,param);
       orderdetails.setArguments(args);
       return orderdetails;
   }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_pay_cord;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mParam=getArguments().getString(Param);
        }
    }
}