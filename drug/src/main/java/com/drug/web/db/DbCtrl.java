package com.drug.web.db;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drug.api.APIResult;
import com.drug.api.response.CommonPageRequest;
import com.drug.entity.DBBackLog;
import com.drug.service.db.DBService;
import com.drug.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;

@RestController
@RequestMapping("/db")
public class DbCtrl {

    @Autowired
    private DBService dbService;


    @RequestMapping(value = "/list/page/", method = RequestMethod.POST)
    @ResponseBody
    public Page<DBBackLog> list(@RequestBody CommonPageRequest pageRequest) {

        Integer currentPage = pageRequest.getCurrentPage();
        Integer size = pageRequest.getPageSize();
        Object keyword = pageRequest.getKeyword();
        //初始化page
        Page<DBBackLog> page = new Page<DBBackLog>(currentPage, size);

        //设置条件
        QueryWrapper<DBBackLog> wrapper =new QueryWrapper<DBBackLog>();
        //执行查询
        Page<DBBackLog> dbs = dbService.page(page, wrapper);
        return dbs;
    }

    @RequestMapping(value = "/create/", method = RequestMethod.GET)
    @ResponseBody
    public APIResult<?> create() {
        DBBackLog db = new DBBackLog();
        db.setBackTime(new Date());
        db.setUserName(UserUtil.getCurrentPrincipal().getUsername());
        dbService.save(db);
        // 执行数据库备份
        try {
            dbBackUp("root", "123456qwe", "drug", "c:", "drug"+ DateUtil.currentSeconds()+".sql");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return APIResult.newSuccessResult();
    }

    /**
     * 备份数据库db
     * @param root
     * @param pwd
     * @param dbName
     * @param backPath
     * @param backName
     */
    public static void dbBackUp(String root,String pwd,String dbName,String backPath,String backName) throws Exception {
        String pathSql = backPath+backName;
        File fileSql = new File(pathSql);
        //创建备份sql文件
        if (!fileSql.exists()){
            fileSql.createNewFile();
        }
        //mysqldump -hlocalhost -uroot -p123456 db > /home/back.sql
        StringBuffer sb = new StringBuffer();
        sb.append("mysqldump");
        sb.append(" -h127.0.0.1");
        sb.append(" -u"+root);
        sb.append(" -p"+pwd);
        sb.append(" "+dbName+" >");
        sb.append(pathSql);
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("cmd /c"+sb.toString());
    }
}