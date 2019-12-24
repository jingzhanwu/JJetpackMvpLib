package com.jetpack.mvp.annotation;

import com.jetpack.mvp.databinder.IDataBinder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @anthor created by jzw
 * @date 2019/12/19
 * @change
 * @describe model和P层使用此注解
 * 绑定对应的dataBinder，在model变化的时候
 * 可通过此注解对应的dataBinder找到关联的dataBinder从而
 * 实现更新数据
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindDataBinder {
    //要绑定的dataBinder
    Class<? extends IDataBinder>[] dataBinder();
}
