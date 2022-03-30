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
 * 部门表
 */
@ApiModel(value = "部门表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_dept")
public class Dept {
    /**
     * 部门id
     */
    @TableId(value = "dept_id", type = IdType.NONE)
    @ApiModelProperty(value = "部门id")
    private Long deptId;

    /**
     * 上级部门id
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value = "上级部门id")
    private Long parentId;

    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    @ApiModelProperty(value = "部门名称")
    private String deptName;

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

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_DEPT_NAME = "dept_name";

    public static final String COL_ORDER_NUM = "order_num";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFY_TIME = "modify_time";
}