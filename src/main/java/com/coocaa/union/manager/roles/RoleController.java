package com.coocaa.union.manager.roles;

import com.coocaa.union.manager.BaseController;
import com.coocaa.union.manager.applications.Application;
import com.coocaa.union.manager.exception.BaseJSONException;
import com.coocaa.union.manager.exception.ErrorCodes;
import com.coocaa.union.manager.utils.ResponseObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/api/role/")
public class RoleController extends BaseController {

    @Autowired
    RoleRepository repository;
    @Autowired
    RoleService service;

    @RequestMapping(value = {"/update"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseObject<Boolean> update(@RequestBody Role role) {
        if (role.getRoleId() == null) {
            throw new BaseJSONException(ErrorCodes.INVALID_INPUT_PARAMS, "RoleId 无效");
        }
        Role oldEneity = repository.findById(role.getRoleId()).orElseThrow(() -> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        oldEneity.setName(role.getName());
        oldEneity.setRoleKey(role.getRoleKey());
        oldEneity.setStatus(role.getStatus());
        oldEneity.setModifyTime(new Date());
        if(role.getApplication() !=null && oldEneity.getApplication().getAppId().equals(role.getApplication().getAppId()) ){
            oldEneity.setApplication(new Application(role.getApplication().getAppId()));
        }
        service.save(oldEneity);

        return ResponseObject.success(true);
    }

    @RequestMapping(value = {"/new"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseObject<Boolean> addNew(@RequestBody Role role) {
        role.setRoleId(null);
        role.setCreateTime(new Date());
        role.setModifyTime(new Date());
        service.save(role);
        return ResponseObject.success(true);
    }


    @RequestMapping(value = {"/detail"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseObject<Role> info(Integer id) {
        if (null == id) {
            throw new BaseJSONException(ErrorCodes.INVALID_INPUT_PARAMS, "查询id为空");
        }
        Role a = repository.findById(id).orElseThrow(() -> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        return ResponseObject.success(a);
    }

    @RequestMapping(value = {"/list"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseObject<Page<Role>> list(
            SearchRoleModel searchModel,
            @PageableDefault(page = 0, size = 50, sort = {"roleId"}, direction = Sort.Direction.DESC)
                    Pageable pageable) {
        Page<Role> a = repository.findAll(specification(searchModel), pageable);

        return ResponseObject.success(a);
    }

    /**
     * 指定动态查询条件
     *
     * @param searchModel
     * @return
     */
    private Specification<Role> specification(final SearchRoleModel searchModel) {
        return new Specification<Role>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(searchModel.getName())) {
                    predicates.add(cb.like(root.get(Role_.name), searchModel.getName() + "%"));
                }
                if (StringUtils.isNotBlank(searchModel.getRoleKey())) {
                    predicates.add(cb.like(root.get(Role_.roleKey), searchModel.getRoleKey() + "%"));
                }
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
    }

    @RequestMapping(value = {"/delete"}, method = {RequestMethod.GET, RequestMethod.POST}
    )
    @ResponseBody
    public ResponseObject<Boolean> info(Integer[] ids) {
        if (null == ids || ids.length == 0) {
            throw new BaseJSONException(ErrorCodes.INVALID_INPUT_PARAMS, "请选择要删除的用户");
        }
        service.deleteById(ids);

        return ResponseObject.success(true);
    }

    @RequestMapping(value = {"valid/list"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseObject<List<Role>> list() {
        List<Role> a = repository.findByStatus(1);
        List<Role> filter = a.stream().filter((item) -> item.getApplication().getStatus() == 1).collect(Collectors.toList());;
        return ResponseObject.success(filter);
    }

    /**
     * 查询接入应用所拥有的权限。
     * @param user
     * @return
     */
    @RequestMapping(value = {"valid/list/client"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseObject<List<Role>> list(Principal user) {
        Integer appId = getAppId(user);

        List<Role> a = repository.findByStatus(1);
        List<Role> result = a.stream().filter(role -> role.getApplication().getAppId().equals(appId)).collect(Collectors.toList());
        return ResponseObject.success(result);
    }



}
