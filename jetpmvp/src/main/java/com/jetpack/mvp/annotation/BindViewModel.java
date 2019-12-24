package com.jetpack.mvp.annotation;

import com.jetpack.mvp.base.BaseViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @anthor created by jzw
 * @date 2019/12/19
 * @change
 * @describe Activity或者Fragment使用此注解
 * 绑定对应的viewModel
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindViewModel {
    //要绑定的ViewModel
    Class<? extends BaseViewModel> value();
}
