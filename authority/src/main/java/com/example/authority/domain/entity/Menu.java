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

import java.util.Date;

/**
 * 菜单表
 */
@ApiModel(value = "菜单表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_menu")
public class Menu {
    /**
     * 菜单/按钮id
     */
    @TableId(value = "menu_id", type = IdType.NONE)
    @ApiModelProperty(value = "菜单/按钮id")
    private Long menuId;

    /**
     * 上级菜单id
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value = "上级菜单id")
    private Long parentId;

    /**
     * 菜单/按钮名称
     */
    @TableField(value = "menu_name")
    @ApiModelProperty(value = "菜单/按钮名称")
    private String menuName;

    /**
     * 菜单url
     */
    @TableField(value = "url")
    @ApiModelProperty(value = "菜单url")
    private String url;

    /**
     * 权限标识
     */
    @TableField(value = "perms")
    @ApiModelProperty(value = "权限标识")
    private String perms;

    /**
     * 图标
     */
    @TableField(value = "icon")
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 类型 0菜单 1按钮
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value = "类型 0菜单 1按钮")
    private String type;

    /**
     * 排序
     */
    @TableField(value = "order_num")
    @ApiModelProperty(value = "排序")
    private Long orderNum;

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

    public static final String COL_MENU_ID = "menu_id";

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_MENU_NAME = "menu_name";

    public static final String COL_URL = "url";

    public static final String COL_PERMS = "perms";

    public static final String COL_ICON = "icon";

    public static final String COL_TYPE = "type";

    public static final String COL_ORDER_NUM = "order_num";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFY_TIME = "modify_time";
}