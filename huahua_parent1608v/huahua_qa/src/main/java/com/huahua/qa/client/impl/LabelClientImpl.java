/**
 * Date:    2019/5/5 8:45
 * <author>
 * 陈柏
 */
package com.huahua.qa.client.impl;

import com.huahua.qa.client.LabelClient;
import huahua.common.Result;
import huahua.common.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class LabelClientImpl implements LabelClient {

    @Override
    public Result queryById(String labelId) {
        return new Result(false, StatusCode.ERROR,"熔断器启动了");
    }

}
