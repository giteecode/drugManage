package com.drug.web.room;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drug.api.APIResult;
import com.drug.entity.room.RoomStorage;
import com.drug.entity.room.RoomStorageLog;
import com.drug.entity.storage.StorageOperatorTypeEnum;
import com.drug.service.drug.DrugService;
import com.drug.service.room.RoomStorageLogService;
import com.drug.service.room.RoomStorageService;
import com.drug.service.user.UserService;
import com.drug.utils.Collections;
import com.drug.utils.UserUtil;
import com.drug.web.room.request.RoomStorageLogSearchParamRequest;
import com.drug.web.storage.response.StorageOperatorTypeEnumResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/roomStorageLog")
public class RoomStorageLogCtrl {

    @Autowired
    private RoomStorageLogService roomStorageLogService;

    @Autowired
    private RoomStorageService roomStorageService;

    @Autowired
    private UserService userService;

    @Autowired
    private DrugService drugService;

    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    @ResponseBody
    public List<RoomStorageLog> findAll() {
        //获取所有的
        return roomStorageLogService.list();
    }

    @RequestMapping(value = "/list/page/", method = RequestMethod.POST)
    @ResponseBody
    public IPage<RoomStorageLog> list(@RequestBody RoomStorageLogSearchParamRequest pageRequest) {

        Integer currentPage = pageRequest.getCurrentPage();
        Integer size = pageRequest.getPageSize();
        Object keyword = pageRequest.getKeyword();
        //初始化page
        Page<RoomStorageLog> page = new Page<RoomStorageLog>(currentPage, size);
        //执行查询
        IPage<RoomStorageLog> drugPage = roomStorageLogService.getByQueryParams(page, pageRequest);

        drugPage.getRecords().forEach(item->{
            if(item.getUserId() != null){
                item.setUserName(userService.getById(item.getUserId()).getUsername());
            }

            if(item.getDrugId() != null){
                item.setDrugName(drugService.getById(item.getDrugId()).getName());
            }

            if(item.getOperatorType() != null){
                item.setTypeName(StorageOperatorTypeEnum.findById(item.getOperatorType()).getName());
            }

        });
        return drugPage;
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    @ResponseBody
    public APIResult<?> save(@RequestBody RoomStorageLog roomStorageLog) {
        //此处只能新增不会更新
        roomStorageLog.setUserId(UserUtil.getCurrentPrincipal().getId());
        roomStorageLog.setCreateTime(new Date());
        roomStorageLogService.saveAndUpdateStorage(roomStorageLog);
        return APIResult.newSuccessResult();
    }

    /**
     * 保存前检查是否符合保存
     * 1. 药品ID 之前库存不存在  （类型只能是入库，否则提示类型只能是入库）
     * 2. 药品ID 之前库存在但是现在的类型操作的是减库存，而且数量大于库存拥有的，提示数量过大不能操作
     * else success
     * @param roomStorageLog
     * @return
     */
    @RequestMapping(value = "/save/check", method = RequestMethod.POST)
    @ResponseBody
    public String saveCheck(@RequestBody RoomStorageLog roomStorageLog) {
        RoomStorage roomStorage = null;
        //设置条件
        QueryWrapper<RoomStorage> roomStorageWrapper =new QueryWrapper<RoomStorage>();
        //eq是等于，ge是大于等于，gt是大于，le是小于等于，lt是小于，like是模糊查询
        if(roomStorageLog.getDrugId() != null){
            roomStorageWrapper.eq("drug_id", roomStorageLog.getDrugId());
            List<RoomStorage> roomStorages = roomStorageService.list(roomStorageWrapper);
            if (Collections.isNotEmpty(roomStorages)) {
                roomStorage = roomStorages.get(0); // 理论上只有一个
            }
        }

        int type = roomStorageLog.getOperatorType();

        //  1. 药品ID 之前库存不存在  （类型只能是入库，否则提示类型只能是入库）
        if(roomStorage == null && type != StorageOperatorTypeEnum.IN.getId()) {
            return "库存不存在此药品，请选择类型入库";
        }

        // 2. 药品ID 之前库存在但是现在的类型操作的是减库存，而且数量大于库存拥有的，提示数量过大不能操作
        if(roomStorage != null
                && roomStorage.getStockNum() != null
                && roomStorage.getStockNum() < roomStorageLog.getNum()
                && (type == StorageOperatorTypeEnum.DAMAGE.getId() || type == StorageOperatorTypeEnum.DEPARTMENT.getId())) {
            return "减库存数量大于库存拥有的:" + roomStorage.getStockNum();
        }

        return "success";
    }

    @ApiOperation(value = "根据ID加载详情")
    @ApiImplicitParam(dataType = "int", example = "1", required = true, name = "id", allowEmptyValue = false, value = "ID")
    @RequestMapping(value = "/loadDetail/{id:[0-9]+}", method = RequestMethod.GET)
    @ResponseBody
    public RoomStorageLog loadDetail(@PathVariable(name = "id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        //获取当前对象
        RoomStorageLog roomStorageLog = roomStorageLogService.getById(id);
        return roomStorageLog;
    }

    @RequestMapping(value = "/typeList/", method = RequestMethod.GET)
    @ResponseBody
    public List<StorageOperatorTypeEnumResponse> typeList() {
        StorageOperatorTypeEnum[] listEnum = StorageOperatorTypeEnum.values();
        List<StorageOperatorTypeEnumResponse> list = new ArrayList<StorageOperatorTypeEnumResponse>();
        for (StorageOperatorTypeEnum storageOperatorTypeEnum : listEnum) {
            list.add(StorageOperatorTypeEnumResponse.parseResponse(storageOperatorTypeEnum));
        }
        return list;
    }
}