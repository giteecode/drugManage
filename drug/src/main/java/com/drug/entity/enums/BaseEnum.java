package com.drug.entity.enums;

import java.io.Serializable;

public interface BaseEnum extends Serializable{
    /**
     * 调用枚举的this.name()
     * 如果子类定义name变量,则调用super.name();
     * @return
     */
    String getCode();
     
    static <E extends Enum<E> & BaseEnum> E valueOf(String enumCode,Class<E> clazz) {
        E enumm = Enum.valueOf(clazz, enumCode);
        return enumm;
    }
}
