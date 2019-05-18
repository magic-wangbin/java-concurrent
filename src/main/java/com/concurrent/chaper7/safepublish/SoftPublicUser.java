package com.concurrent.chaper7.safepublish;

import com.concurrent.chaper7.safeclass.UserVo;

/**
 * 仿Collections对容器的包装，将内部成员对象进行线程安全包装
 */
public class SoftPublicUser {
    //对象返回的结果使用一个包装类，然后对包装类中的方法实行线程安全
    private final UserVo user;

    public UserVo getUser() {
        return user;
    }

    public SoftPublicUser(UserVo user) {
        this.user = new SynUser(user);
    }

    private static class SynUser extends UserVo {
        private final UserVo userVo;
        private final Object lock = new Object();

        public SynUser(UserVo userVo) {
            this.userVo = userVo;
        }

        @Override
        public int getAge() {
            synchronized (lock) {
                return userVo.getAge();
            }
        }

        @Override
        public void setAge(int age) {
            synchronized (lock) {
                userVo.setAge(age);
            }
        }
    }

}
