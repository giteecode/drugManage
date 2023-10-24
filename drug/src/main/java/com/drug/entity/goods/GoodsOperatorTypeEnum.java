package com.drug.entity.goods;

import com.drug.entity.enums.BaseEnum;

public enum GoodsOperatorTypeEnum implements BaseEnum {

//    入库/退库/报升/报损/退回/科室领用
    IN (1, "入库", true),
    RETURN (2, "退库", true),
    UP (3, "报升", true),
    DAMAGE (4, "报损", false),
    BACK (5, "退回", true),
    DEPARTMENT (6, "科室领用", false);

    protected Integer id;
    protected String name;
    protected Boolean isAdd; // 是否增加数量

    private GoodsOperatorTypeEnum(){

    }

    private GoodsOperatorTypeEnum(Integer id, String name, Boolean isAdd){
        this.id = id;
        this.name = name;
        this.isAdd = isAdd;
    };

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAdd(Boolean add) {
        isAdd = add;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAdd() {
        return isAdd;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getCode() {
        return this.getCode();
    }

    public static GoodsOperatorTypeEnum findById(Integer id) {
        if(id == null) {
            return null;
        }
        for (GoodsOperatorTypeEnum enumObj : GoodsOperatorTypeEnum.values()) {
            if(enumObj.getId().equals(id) )
                return enumObj;
        }
        return null;
    }
}
