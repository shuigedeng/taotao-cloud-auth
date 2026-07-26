/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taotao.cloud.auth.authentication.details.strategy.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>部门信息 </p>
 *
 */
@Schema(title = "部门")
@Entity
@Table(
        name = "sys_department",
        indexes = {@Index(name = "sys_department_id_idx", columnList = "department_id")})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "departmentId")
@Cacheable
// @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region =
// UpmsConstants.REGION_SYS_DEPARTMENT)
public class SysDepartment {

    @Schema(title = "部门ID")
    @Id
    @UuidGenerator
    @Column(name = "department_id", length = 64)
    private String departmentId;

    @Schema(title = "部门名称")
    @Column(name = "department_name", length = 200)
    private String departmentName;

    @Schema(title = "4A标准部门ID")
    @Column(name = "a4_biz_dept_id", length = 64)
    private String a4BizDeptId;

    @Schema(title = "标准部门代码")
    @Column(name = "biz_dept_code", length = 60)
    private String bizDeptCode;

    @Schema(title = "标准部门说明")
    @Column(name = "biz_dept_desc", length = 256)
    private String bizDeptDesc;

    @Schema(title = "标准部门ID")
    @Column(name = "biz_dept_id", length = 64)
    private String bizDeptId;

    @Schema(title = "标准部门名称")
    @Column(name = "biz_dept_name", length = 200)
    private String bizDeptName;

    @Schema(title = "标准部门类型")
    @Column(name = "biz_dept_type", length = 30)
    private String bizDeptType;

    @Schema(title = "分区代码")
    @Column(name = "partition_code", length = 256)
    private String partitionCode;

    @Schema(title = "部门简称")
    @Column(name = "short_name", length = 200)
    private String shortName;

    @Schema(title = "上级部门ID")
    @Column(name = "parent_id", length = 64)
    private String parentId;

    @Schema(title = "所属单位ID")
    @Column(name = "organization_id", length = 64)
    private String organizationId;

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */

    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * 设置
     *
     * @param departmentId departmentId
     * @return 无返回值
     * @since 2022.03
     */

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */

    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * 设置
     *
     * @param departmentName departmentName
     * @return 无返回值
     * @since 2022.03
     */

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */

    public String getA4BizDeptId() {
        return a4BizDeptId;
    }

    /**
     * 设置
     *
     * @param a4BizDeptId a4BizDeptId
     * @return 无返回值
     * @since 2022.03
     */

    public void setA4BizDeptId(String a4BizDeptId) {
        this.a4BizDeptId = a4BizDeptId;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */

    public String getBizDeptCode() {
        return bizDeptCode;
    }

    /**
     * 设置
     *
     * @param bizDeptCode bizDeptCode
     * @return 无返回值
     * @since 2022.03
     */

    public void setBizDeptCode(String bizDeptCode) {
        this.bizDeptCode = bizDeptCode;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */

    public String getBizDeptDesc() {
        return bizDeptDesc;
    }

    /**
     * 设置
     *
     * @param bizDeptDesc bizDeptDesc
     * @return 无返回值
     * @since 2022.03
     */

    public void setBizDeptDesc(String bizDeptDesc) {
        this.bizDeptDesc = bizDeptDesc;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */

    public String getBizDeptId() {
        return bizDeptId;
    }

    /**
     * 设置
     *
     * @param bizDeptId bizDeptId
     * @return 无返回值
     * @since 2022.03
     */

    public void setBizDeptId(String bizDeptId) {
        this.bizDeptId = bizDeptId;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */

    public String getBizDeptName() {
        return bizDeptName;
    }

    /**
     * 设置
     *
     * @param bizDeptName bizDeptName
     * @return 无返回值
     * @since 2022.03
     */

    public void setBizDeptName(String bizDeptName) {
        this.bizDeptName = bizDeptName;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */

    public String getBizDeptType() {
        return bizDeptType;
    }

    /**
     * 设置
     *
     * @param bizDeptType bizDeptType
     * @return 无返回值
     * @since 2022.03
     */

    public void setBizDeptType(String bizDeptType) {
        this.bizDeptType = bizDeptType;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */

    public String getPartitionCode() {
        return partitionCode;
    }

    /**
     * 设置
     *
     * @param partitionCode partitionCode
     * @return 无返回值
     * @since 2022.03
     */

    public void setPartitionCode(String partitionCode) {
        this.partitionCode = partitionCode;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */

    public String getShortName() {
        return shortName;
    }

    /**
     * 设置
     *
     * @param shortName shortName
     * @return 无返回值
     * @since 2022.03
     */

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */

    public String getParentId() {
        return parentId;
    }

    /**
     * 设置
     *
     * @param parentId parentId
     * @return 无返回值
     * @since 2022.03
     */

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */

    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * 设置
     *
     * @param organizationId organizationId
     * @return 无返回值
     * @since 2022.03
     */

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysDepartment that = (SysDepartment) o;
        return Objects.equal(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(departmentId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("departmentId", departmentId)
                .add("departmentName", departmentName)
                .add("a4BizDeptId", a4BizDeptId)
                .add("bizDeptCode", bizDeptCode)
                .add("bizDeptDesc", bizDeptDesc)
                .add("bizDeptId", bizDeptId)
                .add("bizDeptName", bizDeptName)
                .add("bizDeptType", bizDeptType)
                .add("partitionCode", partitionCode)
                .add("shortName", shortName)
                .add("parentId", parentId)
                .add("organizationId", organizationId)
                .toString();
    }
}
