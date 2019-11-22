package com.zhkj.backstage.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhkj.backstage.R;
import com.zhkj.backstage.adapter.LaterorderAdapter;
import com.zhkj.backstage.base.BaseActivity;
import com.zhkj.backstage.base.BaseResult;
import com.zhkj.backstage.bean.WorkOrder;
import com.zhkj.backstage.contract.OrderListContract;
import com.zhkj.backstage.model.OrderListModel;
import com.zhkj.backstage.presenter.OrderListPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*最新工单*/
public class WorkOrderListActivity extends BaseActivity<OrderListPresenter, OrderListModel> implements View.OnClickListener, OrderListContract.View {


    @BindView(R.id.new_order)
    RecyclerView mNewOrder;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.view)
    View mView;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private List<WorkOrder.DataBean> list = new ArrayList<>();
    private LaterorderAdapter adapter;
    private int page = 1;
    private String date;
    private String name;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    @Override
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
//        mImmersionBar.statusBarDarkFont(true, 0.2f); //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
        mImmersionBar.statusBarView(mView);
        mImmersionBar.keyboardEnable(true);
        mImmersionBar.init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_workorder_list;
    }

    @Override
    protected void initData() {
        myClipboard = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        adapter = new LaterorderAdapter(R.layout.item_new_order, list);
        mNewOrder.setLayoutManager(new LinearLayoutManager(mActivity));
        mNewOrder.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(mActivity, TopDetailsActivity.class);
                intent.putExtra("OrderId",list.get(position).getOrderID());
                startActivity(intent);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.iv_copy:
                        String id = list.get(position).getOrderID();
                        myClip = ClipData.newPlainText("", id);
                        myClipboard.setPrimaryClip(myClip);
                        ToastUtils.showShort("复制成功");
                        break;
                }
            }
        });

        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                list.clear();
                showProgress();
                getData();
//                mPresenter.GetOrderInfoList(null, null, null, null, null, null, null, date, null, null, String.valueOf(page), "10");
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getData();
//                mPresenter.GetOrderInfoList(null, null, null, null, null, null, null, date, null, null, String.valueOf(page), "10");
            }
        });
    }

    @Override
    protected void initView() {
        name = getIntent().getStringExtra("name");
        mTvTitle.setText(name);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // HH:mm:ss  HH:24小时制  hh:12小时制
        date = dateFormat.format(new Date());
        showProgress();
        getData();
//        mPresenter.GetOrderInfoList(null, null, null, null, null, null, null, date, null, null, String.valueOf(page), "10");
    }

    private void getData() {
        switch (name){
            case "最新工单":
                mPresenter.GetOrderInfoList(null, null, null, null, null, null, null, date, null, null, String.valueOf(page), "10");
                break;
            case "配件工单":
                mPresenter.GetOrderInfoList(null, null, null, null, null, null, null, null, "1", null, String.valueOf(page), "10");
                break;
            case "质保工单":
                mPresenter.GetOrderInfoList(null, null, "3", null, null, null, null, null, null, null, String.valueOf(page), "10");
                break;
            case "远程费工单":
                mPresenter.GetOrderInfoList(null, null, null, "9", null, null, null, null, null, null, String.valueOf(page), "10");
                break;
            case "留言工单":
                mPresenter.GetOrderInfoList(null, null, null, null, null, null, null, null, null, "1", String.valueOf(page), "10");
                break;
            case "投诉工单":
                mPresenter.GetOrderInfoList(null, null, null, null, null, null, null, null, null, null, String.valueOf(page), "10");
                break;
            case "完成工单":
                mPresenter.GetOrderInfoList(null, null, null, "7", null, null, null, null, null, null, String.valueOf(page), "10");
                break;
            case "废除工单":
                mPresenter.GetOrderInfoList(null, null, null, "-1", null, null, null, null, null, null, String.valueOf(page), "10");
                break;
        }
    }

    @Override
    protected void setListener() {
        mIvBack.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }


    @Override
    public void GetOrderInfoList(BaseResult<WorkOrder> baseResult) {
        switch (baseResult.getStatusCode()) {
            case 200:
                list.addAll(baseResult.getData().getData());
                adapter.setNewData(list);
                hideProgress();
                break;
        }
    }
}