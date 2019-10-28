package com.yy.electric.maintenance.feature;

import com.yy.electric.maintenance.api.Callback;
import com.yy.electric.maintenance.api.ITask;
import com.yy.electric.maintenance.base.IRequest;
import com.yy.electric.maintenance.task.RequestDataTask;
import com.yy.electric.maintenance.util.LogUtil;

public class BaseDataPresenter<T> implements BaseDataContract.Presenter<T> {

    private static final String TAG = "BaseDataPresenter";
    private BaseDataContract.View<T> view;
    private String url;
    private ITask<T> task;
    private IRequest request;
    private Class<T> clazz;

    public BaseDataPresenter(BaseDataContract.View<T> view, IRequest request, Class<T> clazz) {
        this.view = view;
        this.url = url;
        this.request = request;
        this.clazz = clazz;
        this.view.setPersonal(this);
    }

    @Override
    public void start() {
        request();
    }

    @Override
    public void request() {
        if (url == null) {
            LogUtil.e(TAG, "loadBitmap() --- url is null");
            return;
        }
        if (task == null) {
            task = new RequestDataTask<>(clazz);
        }
        view.requestStart();
        task.startTask(request, new Callback<T>() {
            @Override
            public void result(T result) {
                view.result(result);
                view.requestEnd();
            }
        });

    }

}
