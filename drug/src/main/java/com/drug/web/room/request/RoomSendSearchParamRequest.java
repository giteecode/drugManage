package com.drug.web.room.request;

import com.drug.api.response.CommonPageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoomSendSearchParamRequest extends CommonPageRequest {

    protected String drugName;
    protected String customerName;
    protected String customerNo;

}
