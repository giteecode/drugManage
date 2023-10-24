package com.drug.web.storage.response;

import com.drug.api.request.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StorageChartsResponse extends BaseResponse {
    // 库存数量
    protected Integer storageNum;
    // 药品
    protected String drugName;

}
