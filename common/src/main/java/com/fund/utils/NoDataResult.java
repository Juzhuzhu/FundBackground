package com.fund.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 内容
 * <p>
 * Create at 2023/02/25 00:09
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/25
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoDataResult implements Serializable {
    @JsonProperty
    protected boolean success = false;
    @JsonProperty
    protected Integer errorCode;
    @JsonProperty
    protected String errorMsg;

    protected NoDataResult() {
    }

    public static NoDataResult success() {
        NoDataResult result = new NoDataResult();
        result.success = true;
        return result;
    }

}
