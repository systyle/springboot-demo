package com.cal;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSON;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

/**
 * Created by swx494800 on 2019/1/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTest02 {
    @LocalServerPort
    private int port;
    private URL base;
    @Autowired
    private TestRestTemplate restTemplate;
    @Before
    public void setUp() throws Exception{
       this.base= new URL("http://localhost:"+port+"/hello");
    }
    @Test
    public void getHello() throws Exception{
        ResponseEntity<String> responseEntity= restTemplate.getForEntity(base.toString(),String.class);
        System.out.print(JSON.toJSON(responseEntity));

    }

}
