package com.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * atomic ref.
 */
public class AtomicRefTest {
    public static void main(String[] args) {

        User user = new User(1L, "ZHANG SAN");

        AtomicReference<User> atomicReference = new AtomicReference<User>(user);

        User updateUser = new User(2L, "LI SI");

        // 引用的替换
        atomicReference.compareAndSet(user, updateUser);

        System.out.println(atomicReference.get());
        System.out.println(user);

        System.out.println("==================================");
        System.out.println(user.equals(updateUser));
        System.out.println(atomicReference.get().equals(updateUser));

    }

    public static class User {

        public User() {
        }

        public User(Long userId, String userName) {
            this.userId = userId;
            this.userName = userName;
        }

        private Long userId;
        private String userName;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        @Override
        public String toString() {
            return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
        }
    }
}
