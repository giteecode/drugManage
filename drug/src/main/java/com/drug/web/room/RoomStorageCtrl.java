package com.drug.web.room;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drug.api.APIResult;
import com.drug.common.Constant;
import com.drug.entity.room.RoomStorage;
import com.drug.service.drug.DrugService;
import com.drug.service.room.RoomStorageService;
import com.drug.web.room.request.RoomStorageSearchParamRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/roomStorage")
public class RoomStorageCtrl {

    @Autowired
    private RoomStorageService roomStorageService;

    @Autowired
    private DrugService drugService;

    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    @ResponseBody
    public List<RoomStorage> findDrugAll() {
        //获取所有的
        return roomStorageService.list();
    }

    // 查询库存小于50的
    @RequestMapping(value = "/list/charts/", method = RequestMethod.GET)
    @ResponseBody
    public List<RoomStorage> findDrugLe50All() {
        //设置条件
        QueryWrapper<RoomStorage> wrapper =new QueryWrapper<RoomStorage>();
        //eq是等于，ge是大于等于，gt是大于，le是小于等于，lt是小于，like是模糊查询
        wrapper.le("stock_num", Constant.WARN_STORAGE_NUM);
        //获取所有的
        List<RoomStorage> list = roomStorageService.list(wrapper);
        list.forEach(item -> {
            item.setDrugName(drugService.getById(item.getDrugId()).getName());
        });
        return list;
    }

    @RequestMapping(value = "/list/page/", method = RequestMethod.POST)
    @ResponseBody
    public IPage<RoomStorage> list(@RequestBody RoomStorageSearchParamRequest pageRequest) {

        Integer currentPage = pageRequest.getCurrentPage();
        Integer size = pageRequest.getPageSize();
        //初始化page
        Page<RoomStorage> page = new Page<RoomStorage>(currentPage, size);

        IPage<RoomStorage> roomStorageServices = roomStorageService.getByQueryParams(page, pageRequest);
        roomStorageServices.getRecords().forEach(item->{
           if(item.getDrugId() != null) {
               item.setDrugName(drugService.getById(item.getDrugId()).getName());
           }
        });

        return roomStorageServices;
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    @ResponseBody
    public APIResult<?> add(@RequestBody RoomStorage roomStorage) {
        roomStorageService.updateById(roomStorage);
        return APIResult.newSuccessResult();
    }

    @ApiOperation(value = "根据ID加载详情")
    @ApiImplicitParam(dataType = "int", example = "1", required = true, name = "id", allowEmptyValue = false, value = "ID")
    @RequestMapping(value = "/loadDetail/{id:[0-9]+}", method = RequestMethod.GET)
    @ResponseBody
    public RoomStorage loadDetail(@PathVariable(name = "id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        //获取当前对象
        RoomStorage roomStorage = roomStorageService.getById(id);
        return roomStorage;
    }
}