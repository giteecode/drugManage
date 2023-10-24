package com.drug.web.goods.request;

import com.drug.api.response.CommonPageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsStorageLogSearchParamRequest extends CommonPageRequest {

    protected String goodsName;
    protected Integer typeId;

}
