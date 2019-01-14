package com.sample.web.base.controller;

import java.util.Locale;

import com.sample.domain.dto.user.User;
import com.sample.domain.dto.user.UserCriteria;
import com.sample.domain.exception.NoDataFoundException;
import com.sample.domain.service.users.UserService;
import com.sample.web.base.util.WebSecurityUtils;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.sample.common.util.MessageUtils;

public class BaseController {

    public static final String VALIDATION_ERROR = "ValidationError";

    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    UserService userService;

    @Autowired
    protected ModelMapper modelMapper;

    /**
     * 入力エラーの共通メッセージを返します。
     * 
     * @return
     */
    protected String getValidationErrorMessage() {
        return getMessage(VALIDATION_ERROR);
    }

    /**
     * コンテキストを返します。
     * 
     * @return
     */
    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * メッセージを取得します。
     *
     * @param key
     * @param args
     * @return
     */
    protected String getMessage(String key, Object... args) {
        return MessageUtils.getMessage(key, args);
    }

    /**
     * ロケールを指定してメッセージを取得します。
     *
     * @param key
     * @param args
     * @param locale
     * @return
     */
    protected String getMessage(String key, Object[] args, Locale locale) {
        return MessageUtils.getMessage(key, args, locale);
    }

    protected User findUserBy(String email) {
        val criteria = new UserCriteria();
        criteria.setEmail(email);
        return userService.findById(criteria).orElseThrow(() -> new NoDataFoundException("ユーザが見つかりません。email:"+email));
    }

    protected User findMe(){ //TODO 共通化したい
        return findUserBy(WebSecurityUtils.getLoginId());
    }
}
