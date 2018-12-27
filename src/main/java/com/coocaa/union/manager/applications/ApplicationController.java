package com.coocaa.union.manager.applications;

import com.coocaa.union.manager.BaseController;
import com.coocaa.union.manager.exception.BaseJSONException;
import com.coocaa.union.manager.exception.ErrorCodes;
import com.coocaa.union.manager.utils.ResponseObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/api/application")
public class ApplicationController extends BaseController {

    @Autowired
    ApplicationRepository repository;
    @Autowired
    ApplicationService service;


    @RequestMapping(value = {"/update"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseObject<Boolean> update(@RequestBody Application application) {
        if (application.getAppId() == null) {
            throw new BaseJSONException(ErrorCodes.INVALID_INPUT_PARAMS, "ApplicationId 无效");
        }
        Application oldEneity = repository.findById(application.getAppId()).orElseThrow(() -> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        oldEneity.setName(application.getName());
        oldEneity.setAppKey(application.getAppKey());
        oldEneity.setMemo(application.getMemo());
        oldEneity.setStatus(application.getStatus());
        oldEneity.setAppSecret(application.getAppSecret());
        oldEneity.setModifyTime(new Date());
        service.save(oldEneity);

        return ResponseObject.success(true);
    }

    @RequestMapping(value = {"/new"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseObject<Boolean> addNew(@RequestBody Application application) {
        application.setAppId(null);
        application.setCreateTime(new Date());
        application.setModifyTime(new Date());
        service.save(application);
        return ResponseObject.success(true);
    }


    @RequestMapping(value = {"/detail"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseObject<Application> info(Integer id) {
        if (null == id) {
            throw new BaseJSONException(ErrorCodes.INVALID_INPUT_PARAMS, "查询id为空");
        }
        Application a = repository.findById(id).orElseThrow(() -> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        return ResponseObject.success(a);
    }

    @RequestMapping(value = {"/list"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseObject<Page<Application>> list(
            SearchApplicationModel searchModel,
            @PageableDefault(page = 0, size = 50, sort = {"appId"}, direction = Sort.Direction.DESC)
                    Pageable pageable) {
        Page<Application> a = repository.findAll(specification(searchModel), pageable);

        return ResponseObject.success(a);
    }

    /**
     * 指定动态查询条件
     *
     * @param searchModel
     * @return
     */
    private Specification<Application> specification(final SearchApplicationModel searchModel) {
        return new Specification<Application>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Application> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(searchModel.getName())) {
                    predicates.add(cb.like(root.get(Application_.name), searchModel.getName() + "%"));
                }
                if (StringUtils.isNotBlank(searchModel.getAppKey())) {
                    predicates.add(cb.like(root.get(Application_.appKey), searchModel.getAppKey() + "%"));
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
}
