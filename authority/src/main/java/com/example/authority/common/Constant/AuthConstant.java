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
    String AUTH_SHIRO_THREAD_POOL = "authShiroThreadPool";

    String DAY_START_PATTERN_SUFFIX = " 00:00:00";

    String DAY_END_PATTERN_SUFFIX = " 23:59:59";

    /**
     * 允许下载的文件类型，根据需求自己添加（小写）
     */
    String[] VALID_FILE_TYPE = {"xlsx", "zip"};

    String REQUEST_ALL = "/**";

    /**
     * shiro线程名称前缀
     */
    String AUTH_SHIRO_THREAD_NAME_PREFIX = "auth-shiro-thread-";

    String JWT_TIMEOUT = "auth.jwt-timeout";
}
