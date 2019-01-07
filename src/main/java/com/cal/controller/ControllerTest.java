package com.cal.controller;

/**
 * Created by swx494800 on 2019/1/6.
 */
public class ControllerTest {

    public static void main(String[] args) throws Exception {
        LoginService proxy =  (LoginService) MyRPC.getProxy(MyLoginService.class, "127.0.0.1", 3001);
        String result = proxy.login("yinchong");
        System.out.println(result);

        Thread.sleep(2000);
        result = proxy.login("yucui");
        System.out.println(result);

        Thread.sleep(2000);
        result = proxy.login("yinyou");
        System.out.println(result);

    }

    static class MyLoginService implements LoginService{


        @Override
        public String login(String name) {
// TODO Auto-generated method stub
            return name+"cal::";
        }

    }
}
