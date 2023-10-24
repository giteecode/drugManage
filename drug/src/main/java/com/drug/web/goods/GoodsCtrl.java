package com.drug.web.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drug.api.APIResult;
import com.drug.api.response.CommonPageRequest;
import com.drug.entity.goods.Goods;
import com.drug.service.goods.GoodsService;
import com.drug.utils.UserUtil;
import com.drug.web.goods.request.GoodsRequest;
import com.drug.web.goods.response.GoodsResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsCtrl {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    @ResponseBody
    public List<Goods> findGoodsAll() {
        //获取所有的
        return goodsService.list();
    }

    @RequestMapping(value = "/list/page/", method = RequestMethod.POST)
    @ResponseBody
    public Page<Goods> list(@RequestBody CommonPageRequest pageRequest) {

        Integer currentPage = pageRequest.getCurrentPage();
        Integer size = pageRequest.getPageSize();
        Object keyword = pageRequest.getKeyword();
        //初始化page
        Page<Goods> page = new Page<Goods>(currentPage, size);

        //设置条件
        QueryWrapper<Goods> wrapper =new QueryWrapper<Goods>();
        //eq是等于，ge是大于等于，gt是大于，le是小于等于，lt是小于，like是模糊查询
        if(!StringUtils.isEmpty(keyword.toString())){
            wrapper.like("name", keyword);
        }
        //执行查询
        Page<Goods> goods = goodsService.page(page, wrapper);
        return goods;
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    @ResponseBody
    public APIResult<?> addGoods(@RequestBody GoodsRequest goodsRequest) {
        //新建对象
        Goods goods = null;
        //判断ID不为空
        if (goodsRequest.getId() != null) {
            //根据ID查询对象
            goods = goodsService.getById(goodsRequest.getId());
        }
        //获取修改后的对象
        goods = GoodsRequest.parseRequest(goodsRequest, goods);

        if (goodsRequest.getId() == null) {
            goods.setUserId(UserUtil.getCurrentPrincipal().getId());
            goods.setCreateTime(new Date());
        }

        //保存对象
        goodsService.saveOrUpdate(goods);
        return APIResult.newSuccessResult();
    }

    @ApiOperation(value = "根据ID加载详情")
    @ApiImplicitParam(dataType = "int", example = "1", required = true, name = "id", allowEmptyValue = false, value = "ID")
    @RequestMapping(value = "/loadDetail/{id:[0-9]+}", method = RequestMethod.GET)
    @ResponseBody
    public GoodsResponse loadDetail(@PathVariable(name = "id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        //获取当前对象
        Goods goods = goodsService.getById(id);
        GoodsResponse goodsResponse = GoodsResponse.parseResponse(goods);
        return goodsResponse;
    }
}