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

/**
 * 用户数据权限关联表
 */
@ApiModel(value = "用户数据权限关联表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_user_data_permission")
public class UserDataPermission {
    @TableId(value = "user_id", type = IdType.NONE)
    @ApiModelProperty(value = "")
    private Long userId;

    @TableField(value = "dept_id")
    @ApiModelProperty(value = "")
    private Long deptId;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_DEPT_ID = "dept_id";
}