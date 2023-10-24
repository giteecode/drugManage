package com.drug.web.room.request;

import com.drug.api.response.CommonPageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoomSendRequest extends CommonPageRequest {

    protected List<RoomSendRequestDrug> roomSendRequestDrugs;
    protected Integer registerId;

    @Data
    public static class RoomSendRequestDrug {
        public Integer num;
        public Integer drugId;
    }

}
