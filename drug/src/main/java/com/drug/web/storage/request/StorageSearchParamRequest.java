package com.drug.web.storage.request;

import com.drug.api.response.CommonPageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StorageSearchParamRequest extends CommonPageRequest {

    protected String drugName;

}
