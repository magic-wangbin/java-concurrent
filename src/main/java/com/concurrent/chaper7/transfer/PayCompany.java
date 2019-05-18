package com.concurrent.chaper7.transfer;


import com.concurrent.chaper7.transfer.service.ITransfer;
import com.concurrent.chaper7.transfer.service.SafeOperate;
import com.concurrent.chaper7.transfer.service.SafeOperateToo;
import com.concurrent.chaper7.transfer.service.TrasnferAccount;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * <p>
 * 类说明：模拟支付公司转账的动作
 */
public class PayCompany {

    /*执行转账动作的线程*/
    private static class TransferThread extends Thread {

        private String name;
        private UserAccount from;
        private UserAccount to;
        private int amount;
        private ITransfer transfer;

        public TransferThread(String name, UserAccount from, UserAccount to,
                              int amount, ITransfer transfer) {
            this.name = name;
            this.from = from;
            this.to = to;
            this.amount = amount;
            this.transfer = transfer;
        }


        public void run() {
            Thread.currentThread().setName(name);
            try {
                transfer.transfer(from, to, amount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        PayCompany payCompany = new PayCompany();
        UserAccount zhangsan = new UserAccount("zhangsan", 20000);
        UserAccount lisi = new UserAccount("lisi", 20000);


        // 不安全的转账--死锁
//        ITransfer transfer = new TrasnferAccount();

        //
//        ITransfer transfer = new SafeOperate();

        ITransfer transfer = new SafeOperateToo();


        TransferThread zhangsanToLisi = new TransferThread("zhangsanToLisi"
                , zhangsan, lisi, 2000, transfer);
        TransferThread lisiToZhangsan = new TransferThread("lisiToZhangsan"
                , lisi, zhangsan, 4000, transfer);
        zhangsanToLisi.start();
        lisiToZhangsan.start();

    }

}
