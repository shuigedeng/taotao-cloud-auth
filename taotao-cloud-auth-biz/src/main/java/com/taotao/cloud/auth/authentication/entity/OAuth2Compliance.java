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

package com.taotao.cloud.auth.authentication.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.taotao.boot.data.jpa.tenant.BaseEntity;
import com.taotao.boot.security.spring.support.constants.OAuth2Constants;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>用户操作审计 </p>
 *
 *
 * @since : 2022/7/7 18:55
 */
@Entity
@Table(
        name = "oauth2_compliance",
        indexes = {@Index(name = "oauth2_compliance_id_idx", columnList = "compliance_id")})
@Cacheable
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE,
        region = OAuth2Constants.REGION_OAUTH2_COMPLIANCE)
public class OAuth2Compliance extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(name = "compliance_id", length = 64)
    private String complianceId;

    @Column(name = "principal_name", length = 128)
    private String principalName;

    @Column(name = "client_id", length = 100)
    private String clientId;

    @Column(name = "ip_address", length = 20)
    private String ip;

    @Column(name = "is_mobile")
    private Boolean mobile = false;

    @Column(name = "os_name", length = 200)
    private String osName;

    @Column(name = "browser_name", length = 50)
    private String browserName;

    @Column(name = "is_mobile_browser")
    private Boolean mobileBrowser = false;

    @Column(name = "engine_name", length = 50)
    private String engineName;

    @Column(name = "is_mobile_platform")
    private Boolean mobilePlatform = false;

    @Column(name = "is_iphone_or_ipod")
    private Boolean iphoneOrIpod = false;

    @Column(name = "is_ipad")
    private Boolean ipad = false;

    @Column(name = "is_ios")
    private Boolean ios = false;

    @Column(name = "is_android")
    private Boolean android = false;

    @Column(name = "operation")
    private String operation;

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */
    public String getComplianceId() {
        return complianceId;
    }

    /**
     * 设置
     *
     * @param complianceId complianceId
     * @return 无返回值
     * @since 2022.03
     */
    public void setComplianceId(String complianceId) {
        this.complianceId = complianceId;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */
    public String getPrincipalName() {
        return principalName;
    }

    /**
     * 设置
     *
     * @param principalName principalName
     * @return 无返回值
     * @since 2022.03
     */
    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 设置
     *
     * @param clientId clientId
     * @return 无返回值
     * @since 2022.03
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置
     *
     * @param ip ip
     * @return 无返回值
     * @since 2022.03
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取
     *
     * @return 是否成功
     * @since 2022.03
     */
    public Boolean getMobile() {
        return mobile;
    }

    /**
     * 设置
     *
     * @param mobile mobile
     * @return 无返回值
     * @since 2022.03
     */
    public void setMobile(Boolean mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */
    public String getOsName() {
        return osName;
    }

    /**
     * 设置
     *
     * @param osName osName
     * @return 无返回值
     * @since 2022.03
     */
    public void setOsName(String osName) {
        this.osName = osName;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */
    public String getBrowserName() {
        return browserName;
    }

    /**
     * 设置
     *
     * @param browserName browserName
     * @return 无返回值
     * @since 2022.03
     */
    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    /**
     * 获取
     *
     * @return 是否成功
     * @since 2022.03
     */
    public Boolean getMobileBrowser() {
        return mobileBrowser;
    }

    /**
     * 设置
     *
     * @param mobileBrowser mobileBrowser
     * @return 无返回值
     * @since 2022.03
     */
    public void setMobileBrowser(Boolean mobileBrowser) {
        this.mobileBrowser = mobileBrowser;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */
    public String getEngineName() {
        return engineName;
    }

    /**
     * 设置
     *
     * @param engineName engineName
     * @return 无返回值
     * @since 2022.03
     */
    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    /**
     * 获取
     *
     * @return 是否成功
     * @since 2022.03
     */
    public Boolean getMobilePlatform() {
        return mobilePlatform;
    }

    /**
     * 设置
     *
     * @param mobilePlatform mobilePlatform
     * @return 无返回值
     * @since 2022.03
     */
    public void setMobilePlatform(Boolean mobilePlatform) {
        this.mobilePlatform = mobilePlatform;
    }

    /**
     * 获取
     *
     * @return 是否成功
     * @since 2022.03
     */
    public Boolean getIphoneOrIpod() {
        return iphoneOrIpod;
    }

    /**
     * 设置
     *
     * @param iphoneOrIpod iphoneOrIpod
     * @return 无返回值
     * @since 2022.03
     */
    public void setIphoneOrIpod(Boolean iphoneOrIpod) {
        this.iphoneOrIpod = iphoneOrIpod;
    }

    /**
     * 获取
     *
     * @return 是否成功
     * @since 2022.03
     */
    public Boolean getIpad() {
        return ipad;
    }

    /**
     * 设置
     *
     * @param ipad ipad
     * @return 无返回值
     * @since 2022.03
     */
    public void setIpad(Boolean ipad) {
        this.ipad = ipad;
    }

    /**
     * 获取
     *
     * @return 是否成功
     * @since 2022.03
     */
    public Boolean getIos() {
        return ios;
    }

    /**
     * 设置
     *
     * @param ios ios
     * @return 无返回值
     * @since 2022.03
     */
    public void setIos(Boolean ios) {
        this.ios = ios;
    }

    /**
     * 获取
     *
     * @return 是否成功
     * @since 2022.03
     */
    public Boolean getAndroid() {
        return android;
    }

    /**
     * 设置
     *
     * @param android android
     * @return 无返回值
     * @since 2022.03
     */
    public void setAndroid(Boolean android) {
        this.android = android;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */
    public String getOperation() {
        return operation;
    }

    /**
     * 设置
     *
     * @param operation operation
     * @return 无返回值
     * @since 2022.03
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OAuth2Compliance that = (OAuth2Compliance) o;
        return Objects.equal(complianceId, that.complianceId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(complianceId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("complianceId", complianceId)
                .add("principalName", principalName)
                .add("clientId", clientId)
                .add("ip", ip)
                .add("mobile", mobile)
                .add("osName", osName)
                .add("browserName", browserName)
                .add("mobileBrowser", mobileBrowser)
                .add("engineName", engineName)
                .add("mobilePlatform", mobilePlatform)
                .add("iphoneOrIpod", iphoneOrIpod)
                .add("ipad", ipad)
                .add("ios", ios)
                .add("android", android)
                .add("operation", operation)
                .toString();
    }
}
