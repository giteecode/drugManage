package com.drug.web.room;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drug.api.APIResult;
import com.drug.entity.register.Register;
import com.drug.entity.room.RoomSend;
import com.drug.entity.room.RoomStorage;
import com.drug.service.drug.DrugService;
import com.drug.service.register.RegisterService;
import com.drug.service.room.RoomSendService;
import com.drug.service.room.RoomStorageService;
import com.drug.service.user.UserService;
import com.drug.utils.Collections;
import com.drug.web.room.request.RoomSendRequest;
import com.drug.web.room.request.RoomSendSearchParamRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/roomSend")
public class RoomSendCtrl {

    @Autowired
    private RoomStorageService roomStorageService;

    @Autowired
    private UserService userService;

    @Autowired
    private DrugService drugService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private RoomSendService roomSendService;

    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    @ResponseBody
    public List<RoomSend> findAll() {
        //获取所有的
        return roomSendService.list();
    }

    @RequestMapping(value = "/list/page/", method = RequestMethod.POST)
    @ResponseBody
    public IPage<RoomSend> list(@RequestBody RoomSendSearchParamRequest pageRequest) {

        Integer currentPage = pageRequest.getCurrentPage();
        Integer size = pageRequest.getPageSize();
        Object keyword = pageRequest.getKeyword();
        //初始化page
        Page<RoomSend> page = new Page<RoomSend>(currentPage, size);
        //执行查询
        IPage<RoomSend> roomSendPage = roomSendService.getByQueryParams(page, pageRequest);

        roomSendPage.getRecords().forEach(item->{
            if(item.getUserId() != null){
                item.setUserName(userService.getById(item.getUserId()).getUsername());
            }

            if(item.getDrugId() != null){
                item.setDrugName(drugService.getById(item.getDrugId()).getName());
            }

            if(item.getRegisterId() != null){
                Register register = registerService.getById(item.getRegisterId());
                item.setCustomerName(register.getCustomerName());
                item.setCustomerNo(register.getCustomerNo());
            }

        });
        return roomSendPage;
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    @ResponseBody
    public APIResult<?> save(@RequestBody RoomSendRequest roomSendRequest) {
        //此处只能新增不会更新
        roomSendService.saveAndUpdateStorage(roomSendRequest);
        return APIResult.newSuccessResult();
    }

    /**
     * 保存前检查是否符合保存
     * 1. 选择的药品是否库存充足
     * else success
     * @param roomSendRequest
     * @return
     */
    @RequestMapping(value = "/save/check", method = RequestMethod.POST)
    @ResponseBody
    public String saveCheck(@RequestBody RoomSendRequest roomSendRequest) {
        //eq是等于，ge是大于等于，gt是大于，le是小于等于，lt是小于，like是模糊查询
        // 循环所有的药品ID 及 对应的药房库存封装 MAP
        List<RoomSendRequest.RoomSendRequestDrug> roomSendRequestDrugs = roomSendRequest.getRoomSendRequestDrugs();
        if(roomSendRequestDrugs != null && roomSendRequestDrugs.size() > 0){
            for (RoomSendRequest.RoomSendRequestDrug roomSendRequestDrug : roomSendRequestDrugs) {
                Integer drugId = roomSendRequestDrug.getDrugId();
                String drugName = drugService.getById(drugId).getName();
                //设置条件
                QueryWrapper<RoomStorage> roomStorageWrapper =new QueryWrapper<RoomStorage>();
                roomStorageWrapper.eq("drug_id", drugId);
                List<RoomStorage> roomStorages = roomStorageService.list(roomStorageWrapper);
                if (Collections.isNotEmpty(roomStorages)) {
                    RoomStorage roomStorage = roomStorages.get(0); // 理论上只有一个
                    Integer stockNum = roomStorage.getStockNum();
                    Integer sendNum = roomSendRequestDrug.getNum();
                    if(stockNum.compareTo(sendNum) < 0) {
                        return drugName + " 库存数量为" + stockNum + "发药数量为" + sendNum +" 库存不足无法发药";
                    }
                } else {
                    return drugName + " 库存数量为0 无法发药";
                }
            }
        }

        return "success";
    }

    @ApiOperation(value = "根据ID加载详情")
    @ApiImplicitParam(dataType = "int", example = "1", required = true, name = "id", allowEmptyValue = false, value = "ID")
    @RequestMapping(value = "/loadDetail/{id:[0-9]+}", method = RequestMethod.GET)
    @ResponseBody
    public RoomSend loadDetail(@PathVariable(name = "id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        //获取当前对象
        RoomSend roomSend = roomSendService.getById(id);
        return roomSend;
    }
}