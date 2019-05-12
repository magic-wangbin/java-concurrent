package com.concurrent.lock.rw;

public interface GoodInfoService {
    /**
     * 获得商品的信息.
     *
     * @return
     */
    public GoodInfo getNum();

    /**
     * 设置商品的数量.
     *
     * @param number
     */
    public void setNum(int number);
}
