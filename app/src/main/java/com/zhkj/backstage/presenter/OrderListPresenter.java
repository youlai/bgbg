package com.zhkj.backstage.presenter;

import com.zhkj.backstage.base.BaseObserver;
import com.zhkj.backstage.base.BaseResult;
import com.zhkj.backstage.bean.WorkOrder;
import com.zhkj.backstage.contract.OrderListContract;

public class OrderListPresenter extends OrderListContract.Presenter {
    @Override
    public void GetOrderInfoList(String OrderID, String UserID, String TypeID, String State, String ProvinceCode, String CityCode, String AreaCode, String CreateDate, String partsIs, String messageIs,String page, String limit) {
        mModel.GetOrderInfoList(OrderID, UserID, TypeID, State, ProvinceCode, CityCode, AreaCode, CreateDate, partsIs, messageIs,page,limit)
                .subscribe(new BaseObserver<WorkOrder>() {
                    @Override
                    protected void onHandleSuccess(BaseResult<WorkOrder> value) {
                        mView.GetOrderInfoList(value);
                    }
                });
    }
}
