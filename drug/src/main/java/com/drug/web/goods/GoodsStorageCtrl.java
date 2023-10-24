package com.drug.web.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drug.api.APIResult;
import com.drug.common.Constant;
import com.drug.entity.goods.GoodsStorage;
import com.drug.service.goods.GoodsService;
import com.drug.service.goods.GoodsStorageService;
import com.drug.web.goods.request.GoodsStorageSearchParamRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/goodsStorage")
public class GoodsStorageCtrl {

    @Autowired
    private GoodsStorageService goodsStorageService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    @ResponseBody
    public List<GoodsStorage> findGoodsAll() {
        //获取所有的
        return goodsStorageService.list();
    }

    // 查询库存小于50的
    @RequestMapping(value = "/list/charts/", method = RequestMethod.GET)
    @ResponseBody
    public List<GoodsStorage> findDrugLe50All() {
        //设置条件
        QueryWrapper<GoodsStorage> wrapper =new QueryWrapper<GoodsStorage>();
        //eq是等于，ge是大于等于，gt是大于，le是小于等于，lt是小于，like是模糊查询
        wrapper.le("stock_num", Constant.WARN_STORAGE_NUM);
        //获取所有的
        List<GoodsStorage> list = goodsStorageService.list(wrapper);
        list.forEach(item -> {
            item.setGoodsName(goodsService.getById(item.getGoodsId()).getName());
        });
        return list;
    }

    @RequestMapping(value = "/list/page/", method = RequestMethod.POST)
    @ResponseBody
    public IPage<GoodsStorage> list(@RequestBody GoodsStorageSearchParamRequest pageRequest) {

        Integer currentPage = pageRequest.getCurrentPage();
        Integer size = pageRequest.getPageSize();
        //初始化page
        Page<GoodsStorage> page = new Page<GoodsStorage>(currentPage, size);
        IPage<GoodsStorage> goodsStorages = goodsStorageService.getByQueryParams(page, pageRequest);
        goodsStorages.getRecords().forEach(item->{
            if(item.getGoodsId() != null) {
                item.setGoodsName(goodsService.getById(item.getGoodsId()).getName());
            }
        });
        return goodsStorages;
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    @ResponseBody
    public APIResult<?> add(@RequestBody GoodsStorage goodsStorage) {
        goodsStorageService.updateById(goodsStorage);
        return APIResult.newSuccessResult();
    }

    @ApiOperation(value = "根据ID加载详情")
    @ApiImplicitParam(dataType = "int", example = "1", required = true, name = "id", allowEmptyValue = false, value = "ID")
    @RequestMapping(value = "/loadDetail/{id:[0-9]+}", method = RequestMethod.GET)
    @ResponseBody
    public GoodsStorage loadDetail(@PathVariable(name = "id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        //获取当前对象
        GoodsStorage goodsStorage = goodsStorageService.getById(id);
        return goodsStorage;
    }
}