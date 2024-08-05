package com.dewfn.busstation.importantdata.controller;

//@RequestMapping("/importantData")
//@Controller
public class DefaultImportantDataController extends BaseImportantDataController {

    @Override
    public String getUserInfo() {
        return "用户1";
    }

    @Override
    public Object convertResult(String data) {
        return data;
    }
}
