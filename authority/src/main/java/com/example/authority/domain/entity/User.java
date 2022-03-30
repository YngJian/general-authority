package com.example.authority.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

/**
 * 用户表
 */
@ApiModel(value = "用户表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_user")
public class User {

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USERNAME = "username";

    public static final String COL_PASSWORD = "password";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_EMAIL = "email";

    public static final String COL_MOBILE = "mobile";

    public static final String COL_STATUS = "status";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFY_TIME = "modify_time";

    public static final String COL_LAST_LOGIN_TIME = "last_login_time";

    public static final String COL_SSEX = "ssex";

    public static final String COL_IS_TAB = "is_tab";

    public static final String COL_THEME = "theme";

    public static final String COL_AVATAR = "avatar";

    public static final String COL_DESCRIPTION = "description";

    /**
     * 用户状态：有效
     */
    public static final String STATUS_VALID = "1";
    /**
     * 用户状态：锁定
     */
    public static final String STATUS_LOCK = "0";
    /**
     * 默认头像
     */
    public static final String DEFAULT_AVATAR = "default.jpg";
    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "1234qwer";
    /**
     * 性别男
     */
    public static final String SEX_MALE = "0";
    /**
     * 性别女
     */
    public static final String SEX_FEMALE = "1";
    /**
     * 性别保密
     */
    public static final String SEX_UNKNOW = "2";
    /**
     * 黑色主题
     */
    public static final String THEME_BLACK = "black";
    /**
     * 白色主题
     */
    public static final String THEME_WHITE = "white";
    /**
     * TAB开启
     */
    public static final String TAB_OPEN = "1";
    /**
     * TAB关闭
     */
    public static final String TAB_CLOSE = "0";
    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.NONE)
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 部门id
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value = "部门id")
    private Long deptId;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 联系电话
     */
    @TableField(value = "mobile")
    @ApiModelProperty(value = "联系电话")
    private String mobile;

    /**
     * 状态 0锁定 1有效
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态 0锁定 1有效")
    private String status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time")
    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    /**
     * 最近访问时间
     */
    @TableField(value = "last_login_time")
    @ApiModelProperty(value = "最近访问时间")
    private Date lastLoginTime;

    /**
     * 性别 0男 1女 2保密
     */
    @TableField(value = "sex")
    @ApiModelProperty(value = "性别 0男 1女 2保密")
    private String sex;

    /**
     * 是否开启tab，0关闭 1开启
     */
    @TableField(value = "is_tab")
    @ApiModelProperty(value = "是否开启tab，0关闭 1开启")
    private String isTab;

    /**
     * 主题
     */
    @TableField(value = "theme")
    @ApiModelProperty(value = "主题")
    private String theme;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value = "描述")
    private String description;

    @TableField(exist = false)
    @ApiModelProperty(value = "deptIds")
    private String deptIds;

    @TableField(exist = false)
    @ApiModelProperty(value = "stringPermissions")
    private Set<String> stringPermissions;

    @TableField(exist = false)
    @ApiModelProperty(value = "roles")
    private Set<String> roles;

    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;
    /**
     * 角色 ID
     */
    @NotBlank(message = "{required}")
    @TableField(exist = false)
    private String roleId;

    @TableField(exist = false)
    private String roleName;

}