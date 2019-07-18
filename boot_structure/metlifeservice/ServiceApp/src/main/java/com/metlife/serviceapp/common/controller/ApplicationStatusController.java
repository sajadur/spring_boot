package com.metlife.serviceapp.common.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ApplicationStatusController {

    @CrossOrigin
    @ApiOperation(value = "Service Status")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Map<String, String> hello() {
        Map<String, String> model = new HashMap<>();
        model.put("item", "Brain Station 23 Ltd. Metlife Service is running...");

        return model;
    }

}
