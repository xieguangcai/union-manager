package com.coocaa.union.manager.accounts;

import com.coocaa.union.manager.BaseController;
import com.coocaa.union.manager.exception.BaseJSONException;
import com.coocaa.union.manager.exception.ErrorCodes;
import com.coocaa.union.manager.roles.Rights;
import com.coocaa.union.manager.roles.Role;
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
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/api")
public class AccountController extends BaseController {

    @Autowired
    AccountRepository repository;
    @Autowired
    AccountService service;

    @RequestMapping(
            value = {"/user/update"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    @ResponseBody
    public ResponseObject<Boolean> update(@RequestBody Account account) {
        if (account.getAccountId() == null) {
            throw new BaseJSONException(ErrorCodes.INVALID_INPUT_PARAMS, "accountId 无效");
        }
        Account oldEneity = repository.findById(account.getAccountId()).orElseThrow(() -> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        //域账号只允许修改状态。
        if(oldEneity.getType() == 2) {
            oldEneity.setAccountStatus(account.getAccountStatus());
        } else {
            oldEneity.setNickName(account.getNickName());
            oldEneity.setUserName(account.getUserName());
            oldEneity.setEmail(account.getEmail());
            oldEneity.setAccountStatus(account.getAccountStatus());
            oldEneity.setDepartment(account.getDepartment());
            oldEneity.setModifyTime(new Date());
            if (account.getPwd() != null) {
                oldEneity.setPwd(encryptPwd(account.getPwd()));
            }
        }
        service.save(oldEneity);
        return ResponseObject.success(true);
    }

    @RequestMapping(
            value = {"/user/new"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    @ResponseBody
    public ResponseObject<Boolean> addNew(@RequestBody Account account) {
        account.setAccountId(null);
        if(StringUtils.isBlank(account.getPwd()) || account.getPwd().length() < 6){
            throw new BaseJSONException(ErrorCodes.INVALID_INPUT_PARAMS, "请输入至少6位密码");
        }
        if (account.getPwd() != null) {
            account.setPwd(encryptPwd(account.getPwd()));
        }
        account.setCreateTime(new Date());
        account.setModifyTime(new Date());
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


    @RequestMapping(value = "/user/rights")
    @ResponseBody
    public ResponseObject<Rights> userRoles(Integer id) {
        Account account = repository.findById(id).orElseThrow(() -> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY));
        logger.info(account.getRoles().toString());
        Rights rights = new Rights();
        rights.setRoles(account.getRoles());
        rights.setDataItems(account.getDataItems());
        return ResponseObject.success(rights);
    }

    @RequestMapping(value = "/user/save/rights")
    @ResponseBody
    public ResponseObject<Boolean> saveUserRoles(Integer accountId, Integer[] roleIds, Integer[] userDataItems) {
        service.saveAccountRole(accountId, roleIds, userDataItems);
        return ResponseObject.success(true);
    }

    @ResponseBody
    @RequestMapping("/me/changepwd")
    public ResponseObject<Boolean> changePwd(Principal user, String oldPwd, String newPwd){

        if(StringUtils.isAnyBlank(oldPwd, newPwd)){
            throw new BaseJSONException(ErrorCodes.INVALID_INPUT_PARAMS, "缺少参数");
        }
        String userName = user.getName();

        Account account = repository.findAccountByNickName(userName).orElseThrow(()-> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY,"账号不正确"));
        if(account.getType() == 2) {
            throw new BaseJSONException(ErrorCodes.LADP_ACCOUNT_NOT_CHANGE_PWD);
        }
        if(!validPwd(oldPwd, account.getPwd())){
           throw new BaseJSONException(ErrorCodes.INVALID_OLD_PWD);
        }
        account.setPwd(encryptPwd(newPwd));
        repository.save(account);
        return ResponseObject.success(true);
    }

    @RequestMapping(value = "/user/save/rights/apply")
    @ResponseBody
    public ResponseObject<Boolean> saveUserRoles(Principal user, Integer[] roleIds, Integer[] userDataItems) {
        String userName = user.getName();
        Account account = repository.findAccountByNickName(userName).orElseThrow(()-> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY,"账号不正确"));
        service.saveAccountRoleApply(account, roleIds, userDataItems);
        return ResponseObject.success(true);
    }

    /**
     * 获取用户申请的权限列表
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/rights/apply")
    @ResponseBody
    public ResponseObject<Rights> userRoles(Principal user) {
        String userName = user.getName();
        Account account = repository.findAccountByNickName(userName).orElseThrow(()-> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY,"账号不正确"));
        Rights rights = new Rights();
        rights.setRoles(account.getRolesApply());
        rights.setDataItems(account.getDataItemsApply());
        return ResponseObject.success(rights);
    }

    /**
     * 获取用户申请的权限列表
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/user/rights/apply/admin")
    @ResponseBody
    public ResponseObject<Rights> userRolesApplyAdmin(Integer accountId) {
        Account account = repository.findById(accountId).orElseThrow(()-> new BaseJSONException(ErrorCodes.NO_SUCH_ENTITY,"账号不正确"));
        Rights rights = new Rights();
        rights.setRoles(account.getRolesApply());
        rights.setDataItems(account.getDataItemsApply());
        return ResponseObject.success(rights);
    }

    @RequestMapping(value = "/user/save/rights/apply/admin")
    @ResponseBody
    public ResponseObject<Boolean> saveUserRolesApplyAdmin(Integer accountId, Integer[] roleIds, Integer[] userDataItems) {
        service.saveAccountRoleApplyAdmin(accountId, roleIds, userDataItems);
        return ResponseObject.success(true);
    }


}
