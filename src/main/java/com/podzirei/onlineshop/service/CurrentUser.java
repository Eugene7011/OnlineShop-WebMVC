//package com.podzirei.onlineshop.service;
//
//import com.podzirei.onlineshop.entity.User;
//
//public class CurrentUser {
//
//    private static final ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<User>();
//
//    public static void setCurrentUser(User user){
//        USER_THREAD_LOCAL.set(user);
//    }
//
//    public static User getCurrentUser(){
//        return USER_THREAD_LOCAL.get();
//    }
//
//    public static void remove(){
//        USER_THREAD_LOCAL.remove();
//    }
//}
