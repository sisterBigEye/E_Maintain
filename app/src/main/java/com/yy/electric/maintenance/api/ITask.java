package com.yy.electric.maintenance.api;

import com.yy.electric.maintenance.base.IRequest;

public interface ITask<T> {

    // T为返回实体类
    void startTask(IRequest request, Callback<T> c);

    // T为返回实体类
    void startTask(String url, Callback<T> c);
}
