package com.coocaa.union.manager.accounts;

import com.coocaa.union.manager.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author xieguangcai
 * @date 2020/2/17
 */
@RequestMapping(value = "/api")
@Controller
public class DataController {
    @Autowired
    DataGroupRepository repository;


    @ResponseBody
    @RequestMapping(value = "data-groups/valid/list")
    public ResponseObject<List<DataGroup>> allDataItems() {
        List<DataGroup> groupList = repository.findAll(new Sort(Sort.Direction.ASC, "sortId"));
        return ResponseObject.success(groupList);
    }
}
