package com.concurrent.chaper7.transfer.service;

import com.concurrent.chaper7.transfer.UserAccount;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * <p>
 * 类说明：银行转账动作接口
 */
public interface ITransfer {
    void transfer(UserAccount from, UserAccount to, int amount)
            throws InterruptedException;
}