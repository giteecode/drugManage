package com.drug.web.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drug.api.APIResult;
import com.drug.entity.goods.GoodsStorage;
import com.drug.entity.goods.GoodsStorageLog;
import com.drug.entity.storage.StorageOperatorTypeEnum;
import com.drug.service.goods.GoodsService;
import com.drug.service.goods.GoodsStorageLogService;
import com.drug.service.goods.GoodsStorageService;
import com.drug.service.user.UserService;
import com.drug.utils.Collections;
import com.drug.utils.UserUtil;
import com.drug.web.goods.request.GoodsStorageLogSearchParamRequest;
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
@RequestMapping("/goodsStorageLog")
public class GoodsStorageLogCtrl {

    @Autowired
    private GoodsStorageLogService goodsStorageLogService;

    @Autowired
    private GoodsStorageService goodsStorageService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    @ResponseBody
    public List<GoodsStorageLog> findGoodsAll() {
        //获取所有的
        return goodsStorageLogService.list();
    }

    @RequestMapping(value = "/list/page/", method = RequestMethod.POST)
    @ResponseBody
    public IPage<GoodsStorageLog> list(@RequestBody GoodsStorageLogSearchParamRequest pageRequest) {

        Integer currentPage = pageRequest.getCurrentPage();
        Integer size = pageRequest.getPageSize();
        Object keyword = pageRequest.getKeyword();
        //初始化page
        Page<GoodsStorageLog> page = new Page<GoodsStorageLog>(currentPage, size);
        //执行查询
        IPage<GoodsStorageLog> goodsPage = goodsStorageLogService.getByQueryParams(page, pageRequest);

        goodsPage.getRecords().forEach(item->{
            if(item.getUserId() != null){
                item.setUserName(userService.getById(item.getUserId()).getUsername());
            }

            if(item.getGoodsId() != null){
                item.setGoodsName(goodsService.getById(item.getGoodsId()).getName());
            }

            if(item.getOperatorType() != null){
                item.setTypeName(StorageOperatorTypeEnum.findById(item.getOperatorType()).getName());
            }

        });
        return goodsPage;
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    @ResponseBody
    public APIResult<?> save(@RequestBody GoodsStorageLog goodsStorageLog) {
        //此处只能新增不会更新
        goodsStorageLog.setUserId(UserUtil.getCurrentPrincipal().getId());
        goodsStorageLog.setCreateTime(new Date());
        goodsStorageLogService.saveAndUpdateStorage(goodsStorageLog);
        return APIResult.newSuccessResult();
    }

    /**
     * 保存前检查是否符合保存
     * 1. 药品ID 之前库存不存在  （类型只能是入库，否则提示类型只能是入库）
     * 2. 药品ID 之前库存在但是现在的类型操作的是减库存，而且数量大于库存拥有的，提示数量过大不能操作
     * else success
     * @param goodsStorageLog
     * @return
     */
    @RequestMapping(value = "/save/check", method = RequestMethod.POST)
    @ResponseBody
    public String saveCheck(@RequestBody GoodsStorageLog goodsStorageLog) {
        GoodsStorage goodsStorage = null;
        //设置条件
        QueryWrapper<GoodsStorage> goodsStorageWrapper =new QueryWrapper<GoodsStorage>();
        //eq是等于，ge是大于等于，gt是大于，le是小于等于，lt是小于，like是模糊查询
        if(goodsStorageLog.getGoodsId() != null){
            goodsStorageWrapper.eq("goods_id", goodsStorageLog.getGoodsId());
            List<GoodsStorage> goodsStorages = goodsStorageService.list(goodsStorageWrapper);
            if (Collections.isNotEmpty(goodsStorages)) {
                goodsStorage = goodsStorages.get(0); // 理论上只有一个
            }
        }

        int type = goodsStorageLog.getOperatorType();

        //  1. 药品ID 之前库存不存在  （类型只能是入库，否则提示类型只能是入库）
        if(goodsStorage == null && type != StorageOperatorTypeEnum.IN.getId()) {
            return "库存不存在此物资，请选择类型入库";
        }

        // 2. 药品ID 之前库存在但是现在的类型操作的是减库存，而且数量大于库存拥有的，提示数量过大不能操作
        if(goodsStorage != null
                && goodsStorage.getStockNum() != null
                && goodsStorage.getStockNum() < goodsStorageLog.getNum()
                && (type == StorageOperatorTypeEnum.DAMAGE.getId() || type == StorageOperatorTypeEnum.DEPARTMENT.getId())) {
            return "减库存数量大于库存拥有的:" + goodsStorage.getStockNum();
        }

        return "success";
    }

    @ApiOperation(value = "根据ID加载详情")
    @ApiImplicitParam(dataType = "int", example = "1", required = true, name = "id", allowEmptyValue = false, value = "ID")
    @RequestMapping(value = "/loadDetail/{id:[0-9]+}", method = RequestMethod.GET)
    @ResponseBody
    public GoodsStorageLog loadDetail(@PathVariable(name = "id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        //获取当前对象
        GoodsStorageLog goodsStorageLog = goodsStorageLogService.getById(id);
        return goodsStorageLog;
    }

    @RequestMapping(value = "/typeList/", method = RequestMethod.GET)
    @ResponseBody
    public List<StorageOperatorTypeEnumResponse> typeList() {
        StorageOperatorTypeEnum[] listEnum = StorageOperatorTypeEnum.values();
        List<StorageOperatorTypeEnumResponse> list = new ArrayList<StorageOperatorTypeEnumResponse>();
        for (StorageOperatorTypeEnum goodsStorageOperatorTypeEnum : listEnum) {
            list.add(StorageOperatorTypeEnumResponse.parseResponse(goodsStorageOperatorTypeEnum));
        }
        return list;
    }
}