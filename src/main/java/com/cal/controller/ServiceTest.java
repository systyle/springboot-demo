package com.cal.controller;

/**
 * Created by swx494800 on 2019/1/6.
 */
public class ServiceTest {
    public static void main(String[] args) throws Exception {
        MyRPC.Service service = MyRPC.buidler();
        service.setClass(LoginService.class).setObject(new ControllerTest.MyLoginService()).setIntentPort(3001);
        service.start();
    }
}
