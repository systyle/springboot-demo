package com.cal.controller;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyRPC {
    private static ExecutorService threadTool = Executors.newFixedThreadPool(5);


    // 客户端
    public static <T> Object getProxy(Class<T> clazz, String ip, int port)
            throws Exception {
// 返回一个代理对象
        Socket socket = new Socket(ip, port);
        InputStream is = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        OutputStream os = socket.getOutputStream();


        Object instance = Proxy.newProxyInstance(clazz.getClassLoader(),
                clazz.getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method,
                                         Object[] args) throws Throwable {
                        StringBuffer sb = new StringBuffer();
                        sb.append(method.getName()).append("_");
                        int count = args.length;
                        for (int i = 0; i < count; i++) {
                            sb.append(args[i].toString());
                            if (i != count - 1)
                                sb.append("_");
                        }
                        sb.append("\n");
                        os.write(sb.toString().getBytes());
                        os.flush();
                        String result = reader.readLine();
                        if (result.equalsIgnoreCase("null"))
                            return null;
                        return result;
                    }
                });
        return instance;
    }


    public static Service buidler() {
        return new Service();
    }


    // 服务端
    public static class Service {
        private ServerSocket service;
        private Object instacne;
        private Class clazz;


        public Service setObject(Object instance) {
            this.instacne = instance;
            return this;
        }


        public Service setClass(Class clazz) {
            this.clazz = clazz;
            return this;
        }


        public Service setIntentPort(int port) throws Exception {
            service = new ServerSocket(port);
            return this;
        }


        public void start() throws Exception {
            System.out.println("begian connect");


            while (true) {
                final Socket socket = service.accept();
                System.out.println(socket.getLocalAddress() + ":liangshang");
                threadTool.execute(new Runnable() {


                    @Override
                    public void run() {
                        try {
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(socket
                                            .getInputStream()));
                            BufferedWriter bw = new BufferedWriter(
                                    new OutputStreamWriter(socket
                                            .getOutputStream()));


                            while (true) {
                                if (socket.isClosed())
                                    break;
                                String data = br.readLine();
                                if (data != null && !data.equals("")) {
                                    String[] datas = data.split("_");
                                    if (datas != null) {
                                        String methodName = datas[0];
                                        String[] params = new String[datas.length - 1];
                                        Class[] types = new Class[datas.length - 1];
                                        for (int i = 1; i < datas.length; i++) {
                                            if (datas[i].equals(""))
                                                continue;
                                            params[i - 1] = datas[i];
                                            types[i - 1] = String.class;
                                        }


                                        Method method = clazz
                                                .getDeclaredMethod(methodName,
                                                        types);
                                        String result = (String) method.invoke(
                                                instacne, (Object[]) params);
                                        bw.write(result + "\n");
                                        bw.flush();


                                    }
                                }


                            }
                        } catch (Exception e) {
                            System.err.println(e);
                            try {
                                socket.close();
                            } catch (IOException e1) {
// TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }


                    }


                });


            }
        }
    }


}

