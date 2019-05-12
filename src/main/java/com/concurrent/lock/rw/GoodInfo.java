package com.concurrent.lock.rw;

/**
 * 商品信息.
 */
public class GoodInfo {
    /**
     * 商品名称.
     */
    private String goodName;

    /**
     * 总金额.
     */
    private double totalAmount;

    /**
     * 库存数量.
     */
    private int storeNumber;

    public GoodInfo(String goodName, double totalAmount, int storeNumber) {
        this.goodName = goodName;
        this.totalAmount = totalAmount;
        this.storeNumber = storeNumber;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(int storeNumber) {
        this.storeNumber = storeNumber;
    }

    /**
     * 销售操作.
     *
     * @param sellNumber
     */
    public void changeNumber(int sellNumber) {
        this.totalAmount += sellNumber * 25;
        this.storeNumber -= sellNumber;
    }
}
