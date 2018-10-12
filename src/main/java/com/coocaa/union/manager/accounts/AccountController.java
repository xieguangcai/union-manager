package com.coocaa.union.manager.accounts;

import com.coocaa.union.manager.BaseController;
import com.coocaa.union.manager.exception.BaseJSONException;
import com.coocaa.union.manager.exception.ErrorCodes;
import com.coocaa.union.manager.roles.Role;
import com.coocaa.union.manager.utils.ResponseObject;
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
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping(value = "/api")
public class AccountController extends BaseController {

    @Autowired
    AccountRepository repository;
    @Autowired
    AccountService service;

    @RequestMapping(
            value = {"/user/login"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    @ResponseBody
    public ResponseObject<String> userLogin(String userName, String password) {
        return ResponseObject.success(UUID.randomUUID().toString());
    }


    @RequestMapping(
            value = {"/user/update"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    @ResponseBody
    public ResponseObject<Boolean> update(@RequestBody Account account) throws UnsupportedEncodingException {
        if (account.getAccountId() == null) {
            throw new BaseJSONException(ErrorCodes.INVALID_INPUT_PARAMS, "accountId 无效");
        }
        Account oldEneity = repository.findById(account.getAccountId()).orElseThrow(() -> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        oldEneity.setNickName(account.getNickName());
        oldEneity.setUserName(account.getUserName());
        oldEneity.setEmail(account.getEmail());
        oldEneity.setAccountStatus(account.getAccountStatus());
        oldEneity.setDepartment(account.getDepartment());
        if (account.getPwd() != null) {
            oldEneity.setSalt(UUID.randomUUID().toString().replace("-", ""));
            String sPwd = account.getPwd() + oldEneity.getSalt();
            String encryptPwd = DigestUtils.md5DigestAsHex(sPwd.getBytes("utf-8"));
            oldEneity.setPwd(encryptPwd);
        }
        service.save(oldEneity);
        return ResponseObject.success(true);
    }

    @RequestMapping(
            value = {"/user/new"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    @ResponseBody
    public ResponseObject<Boolean> addNew(@RequestBody Account account) throws UnsupportedEncodingException {
        account.setAccountId(null);
        if (account.getPwd() != null) {
            account.setSalt(UUID.randomUUID().toString().replace("-", ""));
            String sPwd = account.getPwd() + account.getSalt();
            String encryptPwd = DigestUtils.md5DigestAsHex(sPwd.getBytes("utf-8"));
            account.setPwd(encryptPwd);
        }
        service.save(account);
        return ResponseObject.success(true);
    }


    @RequestMapping(
            value = {"/user/id"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    @ResponseBody
    public ResponseObject<Account> findById(Integer id) {
        Account a = repository.findById(id).orElseThrow(() -> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        return ResponseObject.success(a);
    }


    @RequestMapping(
            value = {"/user/info"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    @ResponseBody
    public ResponseObject<Account> info(String nickName) {
        Account a = repository.getOne(1);
        return ResponseObject.success(a);
    }


    @RequestMapping(value = {"/user/detail"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseObject<Account> info(Integer id) {
        if (null == id) {
            throw new BaseJSONException(ErrorCodes.INVALID_INPUT_PARAMS, "查询id为空");
        }
        Account a = repository.findById(id).orElseThrow(() -> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        return ResponseObject.success(a);
    }

    @RequestMapping(value = {"/user/list"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseObject<Page<Account>> list(
            SearchAccountModel searchModel,
            @PageableDefault(page = 0, size = 50, sort = {"accountId"}, direction = Sort.Direction.DESC)
                    Pageable pageable) {
        Page<Account> a = repository.findAll(specification(searchModel), pageable);

        List<Account> ax = new ArrayList<>(a.getContent().size());

        for (Account item : a.getContent()) {
            Account tg = new Account();
            BeanUtils.copyProperties(item, tg, "roles");
            ax.add(tg);
        }

        Page<Account> nowMore = new PageImpl<>(ax, a.getPageable(), a.getTotalElements());
        return ResponseObject.success(nowMore);
    }

    /**
     * 指定动态查询条件
     *
     * @param searchModel
     * @return
     */
    private Specification<Account> specification(final SearchAccountModel searchModel) {
        return new Specification<Account>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(searchModel.getNickName())) {
                    predicates.add(cb.like(root.get(Account_.nickName), searchModel.getNickName() + "%"));
                }
                if (StringUtils.isNotBlank(searchModel.getUserName())) {
                    predicates.add(cb.like(root.get(Account_.userName), searchModel.getUserName() + "%"));
                }
                if (searchModel.getAccountStatus() != null && searchModel.getAccountStatus().length > 0) {
                    predicates.add(root.get(Account_.accountStatus).in(searchModel.getAccountStatus()));
                }
                if (searchModel.getCreateTime() != null && searchModel.getCreateTime().length == 2) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get(Account_.createTime), searchModel.getCreateTime()[0]));
                    predicates.add(cb.lessThanOrEqualTo(root.get(Account_.createTime), searchModel.getCreateTime()[1]));
                }
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
    }

    @RequestMapping(
            value = {"/user/delete"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    @ResponseBody
    public ResponseObject<Boolean> info(Integer[] ids) {
        if (null == ids || ids.length == 0) {
            throw new BaseJSONException(ErrorCodes.INVALID_INPUT_PARAMS, "请选择要删除的用户");
        }
        service.deleteById(ids);

        return ResponseObject.success(true);
    }


    @RequestMapping(value = "/user/roles")
    @ResponseBody
    public ResponseObject<Set<Role>> userRoles(Integer id) {
        Account account = repository.findById(id).orElseThrow(() -> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        return ResponseObject.success(account.getRoles());
    }

    @RequestMapping(value = "/user/save/roles")
    @ResponseBody
    public ResponseObject<Boolean> saveUserRoles(Integer accountId, Integer[] roleIds) {
        Account account = repository.findById(accountId).orElseThrow(() -> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        account.getRoles().clear();
        for (Integer roleId : roleIds) {
            account.getRoles().add(new Role(roleId));
        }
        repository.save(account);

        return ResponseObject.success(true);
    }
}
