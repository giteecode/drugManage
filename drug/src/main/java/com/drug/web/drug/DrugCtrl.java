package com.drug.web.drug;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drug.api.APIResult;
import com.drug.api.response.CommonPageRequest;
import com.drug.entity.drug.Drug;
import com.drug.service.drug.DrugService;
import com.drug.service.drug.DrugTypeService;
import com.drug.utils.UserUtil;
import com.drug.web.drug.request.DrugRequest;
import com.drug.web.drug.response.DrugResponse;
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
@RequestMapping("/drug")
public class DrugCtrl {

    @Autowired
    private DrugService drugService;

    @Autowired
    private DrugTypeService drugTypeService;

    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    @ResponseBody
    public List<Drug> findDrugAll() {
        //获取所有的
        return drugService.list();
    }

    @RequestMapping(value = "/list/page/", method = RequestMethod.POST)
    @ResponseBody
    public Page<Drug> list(@RequestBody CommonPageRequest pageRequest) {

        Integer currentPage = pageRequest.getCurrentPage();
        Integer size = pageRequest.getPageSize();
        Object keyword = pageRequest.getKeyword();
        //初始化page
        Page<Drug> page = new Page<Drug>(currentPage, size);

        //设置条件
        QueryWrapper<Drug> wrapper =new QueryWrapper<Drug>();
        //eq是等于，ge是大于等于，gt是大于，le是小于等于，lt是小于，like是模糊查询
        if(!StringUtils.isEmpty(keyword.toString())){
            wrapper.like("name", keyword);
        }

        //执行查询
        Page<Drug> drugs = drugService.page(page, wrapper);
        drugs.getRecords().forEach(item->{
            item.setTypeName(item.getTypeId() == null ? null : drugTypeService.getById(item.getTypeId()).getName());
        });
        return drugs;
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    @ResponseBody
    public APIResult<?> addDrug(@RequestBody DrugRequest drugRequest) {
        //新建对象
        Drug drug = null;
        //判断ID不为空
        if (drugRequest.getId() != null) {
            //根据ID查询对象
            drug = drugService.getById(drugRequest.getId());
        }
        //获取修改后的对象
        drug = DrugRequest.parseRequest(drugRequest, drug);

        if (drugRequest.getId() == null) {
            drug.setUserId(UserUtil.getCurrentPrincipal().getId());
            drug.setCreateTime(new Date());
        }

        //保存对象
        drugService.saveOrUpdate(drug);
        return APIResult.newSuccessResult();
    }

    @ApiOperation(value = "根据ID加载详情")
    @ApiImplicitParam(dataType = "int", example = "1", required = true, name = "id", allowEmptyValue = false, value = "ID")
    @RequestMapping(value = "/loadDetail/{id:[0-9]+}", method = RequestMethod.GET)
    @ResponseBody
    public DrugResponse loadDetail(@PathVariable(name = "id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        //获取当前对象
        Drug drug = drugService.getById(id);
        DrugResponse drugResponse = DrugResponse.parseResponse(drug, drugTypeService.list());
        return drugResponse;
    }
}