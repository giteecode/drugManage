package com.drug.web.goods.request;

import com.drug.api.response.CommonPageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsStorageSearchParamRequest extends CommonPageRequest {

    protected String goodsName;

}
