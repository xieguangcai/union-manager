package com.coocaa.union.manager.accounts;

import com.coocaa.union.manager.BaseController;
import com.coocaa.union.manager.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xieguangcai
 * @date 2020/2/17
 */
@RequestMapping(value = "/api/data-groups")
@Controller
public class DataController extends BaseController {
    @Autowired
    DataGroupRepository repository;


    @ResponseBody
    @RequestMapping(value = "valid/list")
    public ResponseObject<List<DataGroup>> allDataItems() {
        List<DataGroup> groupList = repository.findAll(new Sort(Sort.Direction.ASC, "sortId"));
        return ResponseObject.success(groupList);
    }

    @ResponseBody
    @RequestMapping(value = "valid/list/client")
    public ResponseObject<List<DataGroup>> allDataItems(Principal user) {
        Integer appId = getAppId(user);
        List<DataGroup> groupList = repository.findAll(new Sort(Sort.Direction.ASC, "sortId"));
        List<DataGroup> dataGroupList = groupList.stream().filter(group -> group.getAppId().equals(appId)).collect(Collectors.toList());
        return ResponseObject.success(dataGroupList);
    }
}
