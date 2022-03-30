package com.example.authority.common.Constant;

/**
 * @author yangj
 */
public interface AuthConstant {

    /**
     * 注册用户角色ID
     */
    Long REGISTER_ROLE_ID = 2L;

    /**
     * 排序规则：降序
     */
    String ORDER_DESC = "desc";

    /**
     * 排序规则：升序
     */
    String ORDER_ASC = "asc";

    /**
     * shiro线程池名称
     */
    String SHIRO_THREAD_POOL = "shiroThreadPool";

    String DAY_START_PATTERN_SUFFIX = " 00:00:00";

    String DAY_END_PATTERN_SUFFIX = " 23:59:59";
}