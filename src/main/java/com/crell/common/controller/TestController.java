package com.crell.common.controller;


import com.crell.common.service.TestSer;
import com.crell.core.annotation.BodyFormat;
import com.crell.core.annotation.NotNull;
import com.crell.core.constant.ResponseState;
import com.crell.core.controller.AbstractController;
import com.crell.core.dto.ParamsBody;
import com.crell.core.dto.ReturnBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController extends AbstractController {

    @Autowired
    TestSer testSer;

    @RequestMapping(value = {"/test"},method = RequestMethod.GET)
    public ModelAndView test(HttpServletRequest request){
        return new ModelAndView("test");
    }

    @RequestMapping(value = {"/bodyTest"},method = RequestMethod.POST)
    @BodyFormat(value = "code")
    @NotNull(value = "code")
    @ResponseBody
    public ReturnBody bodyTest(@RequestBody ParamsBody paramsBody,HttpServletRequest request) throws Exception {
        ReturnBody returnbody = new ReturnBody();

//        InputStream is = request.getInputStream();
//        DataInputStream input = new DataInputStream(is);
//        String json =input.readUTF();

        Map<String,Object> body = paramsBody.getBody();
        List<Object> list = new ArrayList<Object>();
        list.add(body.get("name"));
        list.add("测试1");
        list.add("测试2");
        list.add("测试3");

        String aa = testSer.selectUser();
        //String test = userSer.testException();

        returnbody.setStatus(ResponseState.SUCCESS);
        returnbody.setData(list);

        return returnbody;
    }

    @RequestMapping(value = {"/pageTest"},method = RequestMethod.POST)
    @ResponseBody
    public ReturnBody pageTest(@RequestBody ParamsBody paramsBody,HttpServletRequest request) throws Exception {
        ReturnBody returnbody = new ReturnBody();

        List<String> list = testSer.testPage(paramsBody.getBody(),paramsBody.getPage());

        returnbody.setStatus(ResponseState.SUCCESS);
        returnbody.setData(list);

        return returnbody;
    }

    @RequestMapping(value = {"/vmTest"},method = RequestMethod.GET)
    public ModelAndView vmTest(HttpServletRequest request) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("xm", "陈奇");
        ReturnBody returnBody = new ReturnBody();
        returnBody.setMsg("陈奇");
        map.put("name", returnBody);
        ReturnBody returnBody1 = new ReturnBody();
        returnBody1.setMsg("陈奇1");
        List<ReturnBody> list = new ArrayList<ReturnBody>();
        list.add(returnBody);
        list.add(returnBody1);
        map.put("list", list);
        return new ModelAndView("test.vm",map);
    }

}