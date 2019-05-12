package com.concurrent.lock.templatepattern;

/**
 * 类说明：抽象蛋糕模型
 */
public abstract class AbstractCake {
    //
    protected abstract void shape();

    //此步骤非必须
    protected abstract void apply();

    protected boolean shouldApply() {
        return true;
    }

    protected abstract void brake();

    //定义模板步骤
    public void run() {
        this.shape();
        if (this.shouldApply()) {
            this.apply();
        }
        this.brake();
    }
}
