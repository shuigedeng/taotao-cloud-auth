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

package com.taotao.cloud.auth.biz.uaa.configuration;

import com.taotao.boot.captcha.support.core.processor.CaptchaRendererFactory;
import com.taotao.boot.security.spring.authentication.login.extension.ExtensionLoginFilterSecurityConfigurer;
import com.taotao.boot.security.spring.authentication.login.extension.account.service.AccountUserDetailsService;
import com.taotao.boot.security.spring.authentication.login.extension.captcha.service.CaptchaCheckService;
import com.taotao.boot.security.spring.authentication.login.extension.captcha.service.CaptchaUserDetailsService;
import com.taotao.boot.security.spring.authentication.login.extension.face.service.FaceCheckService;
import com.taotao.boot.security.spring.authentication.login.extension.face.service.FaceUserDetailsService;
import com.taotao.boot.security.spring.authentication.login.extension.fingerprint.service.FingerprintUserDetailsService;
import com.taotao.boot.security.spring.authentication.login.extension.gestures.service.GesturesUserDetailsService;
import com.taotao.boot.security.spring.authentication.login.extension.wechatmp.service.WechatMpUserDetailsService;
import com.taotao.boot.security.spring.authentication.login.form.FormLoginFilterSecurityConfigurer;
import com.taotao.boot.security.spring.authentication.login.form.qrcode.service.FormQrcodeService;
import com.taotao.boot.security.spring.authentication.login.form.qrcode.service.FormQrcodeUserDetailsService;
import com.taotao.boot.security.spring.authentication.login.form.sms.service.FormSmsService;
import com.taotao.boot.security.spring.authentication.login.form.sms.service.FormSmsUserDetailsService;
import com.taotao.boot.security.spring.authentication.login.social.oauth2client.SocialDelegateClientRegistrationRepository;
import com.taotao.boot.security.spring.authentication.login.social.oauth2client.SocialLoginFilterSecurityConfigurer;
import com.taotao.boot.security.spring.authentication.response.denied.JsonAccessDeniedHandler;
import com.taotao.boot.security.spring.authentication.response.entrypoint.JsonAuthenticationEntryPoint;
import com.taotao.boot.security.spring.authorization.SecurityAuthorizationManager;
import com.taotao.boot.security.spring.authorization.SecurityMatcherConfigurer;
import com.taotao.boot.security.spring.autoconfigure.properties.SecurityAuthenticationProperties;
import com.taotao.boot.security.spring.support.core.details.TtcUser;
import com.taotao.boot.security.spring.support.core.details.client.ClientDetailsService;
import com.taotao.boot.security.spring.support.filter.ExtensionAndOauth2LoginRefreshTokenFilter;
import com.taotao.boot.security.spring.support.token.OAuth2AccessTokenStore;
import com.taotao.boot.security.spring.support.token.SecurityTokenStrategyConfigurer;
import com.taotao.cloud.auth.biz.management.details.client.Oauth2ClientDetailsService;
import com.taotao.cloud.auth.biz.management.details.user.SecurityUserDetailsService;
import com.taotao.cloud.auth.biz.management.service.OAuth2ApplicationService;
import com.taotao.cloud.auth.biz.management.details.user.strategy.StrategyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import static org.springframework.boot.security.autoconfigure.actuate.web.servlet.EndpointRequest.toAnyEndpoint;

/**
 * <p>默认安全配置 </p>
 *
 * @author shuigedeng
 * @version 2023.07
 * @since 2023-07-04 10:31:08
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfiguration {

	private static final Logger log = LoggerFactory.getLogger(DefaultSecurityConfiguration.class);

	/// **
	// * 跨域过滤器配置
	// */
	// @Bean
	// public CorsFilter corsFilter() {
	//	// 初始化cors配置对象
	//	CorsConfiguration configuration = new CorsConfiguration();
	//	// 设置允许跨域的域名,如果允许携带cookie的话,路径就不能写*号, *表示所有的域名都可以跨域访问
	//	configuration.addAllowedOrigin("http://127.0.0.1:5173");
	//	// 设置跨域访问可以携带cookie
	//	configuration.setAllowCredentials(true);
	//	// 允许所有的请求方法 ==> GET POST PUT Delete
	//	configuration.addAllowedMethod("*");
	//	// 允许携带任何头信息
	//	configuration.addAllowedHeader("*");
	//	// 初始化cors配置源对象
	//	UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
	//	// 给配置源对象设置过滤的参数
	//	// 参数一: 过滤的路径 == > 所有的路径都要求校验是否跨域
	//	// 参数二: 配置类
	//	configurationSource.registerCorsConfiguration("/**", configuration);
	//	// 返回配置好的过滤器
	//	return new CorsFilter(configurationSource);
	// }
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(
		HttpSecurity httpSecurity,
		UserDetailsService userDetailsService,
		SecurityAuthenticationProperties authenticationProperties,
		CaptchaRendererFactory captchaRendererFactory,
		SecurityMatcherConfigurer securityMatcherConfigurer,
		SecurityAuthorizationManager securityAuthorizationManager,
		SecurityTokenStrategyConfigurer ttcTokenStrategyConfigurer,
		SocialDelegateClientRegistrationRepository socialDelegateClientRegistrationRepository,
		ObjectProvider<OAuth2AccessTokenStore> oAuth2AccessTokenStore )
		throws Exception {

		log.info("[Default Security Filter Chain] Auto Configure.");

		// 跨域过滤器一定要添加至security配置中，不然只注入ioc中对于security端点不生效！ 添加跨域过滤器
		// httpSecurity.addFilter(corsFilter());

		// 使用redis存储、读取登录的认证信息
		// httpSecurity.securityContext(context ->
		// context.securityContextRepository(redisSecurityContextRepository));

		// 禁用CSRF 开启跨域
		httpSecurity
			.anonymous(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.sessionManagement(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable);

		httpSecurity
			.authorizeHttpRequests(
				authorizeHttpRequestsCustomizer -> {
					authorizeHttpRequestsCustomizer
						.requestMatchers(securityMatcherConfigurer.getPermitAllArray())
						.permitAll()
						.requestMatchers(securityMatcherConfigurer.getStaticResourceArray())
						.permitAll()
						.requestMatchers(toAnyEndpoint())
						.permitAll()
						.anyRequest()
						.access(securityAuthorizationManager);
				})
			.exceptionHandling(
				exceptionHandlingCustomizer -> {
					exceptionHandlingCustomizer
						.authenticationEntryPoint(new JsonAuthenticationEntryPoint())
						.accessDeniedHandler(new JsonAccessDeniedHandler());
				})
			.oauth2ResourceServer(ttcTokenStrategyConfigurer::from)
			.logout(logoutCustomizer -> {
				logoutCustomizer
					.addLogoutHandler(( request, response, authentication ) -> {
					})
					.logoutSuccessHandler(( request, response, authentication ) -> {
					})
					.clearAuthentication(true);
			})
			// **************************************自定义登录配置***********************************************
			.with(
				new ExtensionLoginFilterSecurityConfigurer<>(),
				( customizer ) -> {
					// 用户+密码登录
					customizer
						.accountLogin(accountLoginConfigurerCustomizer -> {
								accountLoginConfigurerCustomizer
									.accountUserDetailsService(new AccountUserDetailsService() {
											@Override
											public UserDetails loadUserByUsername( String username, String type ) throws UsernameNotFoundException {
												return TtcUser.defaultTest();
											}
										});
							})
						// 用户+密码+验证码登录
						.captchaLogin(captchaLoginConfigurerCustomizer -> {
								captchaLoginConfigurerCustomizer
									.captchaCheckService(new CaptchaCheckService() {
											@Override
											public boolean verifyCaptcha( String verificationCode ) {
												return true;
											}
										})
									.captchaUserDetailsService(new CaptchaUserDetailsService() {
											@Override
											public UserDetails loadUserByUsername( String username, String type ) throws UsernameNotFoundException {
												return TtcUser.defaultTest();
											}
										});
							})
						// 人脸识别登录
						.faceLogin(
							faceLoginConfigurerCustomizer -> {
								faceLoginConfigurerCustomizer
									.faceCheckService(new FaceCheckService() {
											@Override
											public boolean check( String imgBase64 ) throws UsernameNotFoundException {
												return true;
											}
										})
									.faceUserDetailsService(new FaceUserDetailsService() {
											@Override
											public UserDetails loadUserByImgBase64( String imgBase64 ) throws UsernameNotFoundException {
												return TtcUser.defaultTest();
											}
										});
							})
						// 指纹登录
						.fingerprintLogin(
							fingerprintLoginConfigurer -> {
								fingerprintLoginConfigurer
									.fingerprintUserDetailsService(new FingerprintUserDetailsService() {
											@Override
											public UserDetails loadUserByFingerprint( String username ) throws UsernameNotFoundException {
												return TtcUser.defaultTest();
											}
										});
							})
						// 手势登录
						.gesturesLogin(
							fingerprintLoginConfigurer -> {
								fingerprintLoginConfigurer
									.gesturesUserDetailsService(new GesturesUserDetailsService() {
											@Override
											public UserDetails loadUserByPhone( String phone ) throws UsernameNotFoundException {
												return TtcUser.defaultTest();
											}
										});
							})
						// 本机号码一键登录
						.oneClickLogin(oneClickLoginConfigurer -> {
						})
						// 手机扫码登录
						.qrcodeLogin(qrcodeLoginConfigurer -> {
						})
						// 短信登录
						.smsLogin(smsLoginConfigurerCustomizer -> {
						})
						// email登录
						.emailLogin(emailLoginConfigurerCustomizer -> {
						})
						// 微信公众号登录
						.wechatMpLogin(
							mpLoginConfigurer -> {
								mpLoginConfigurer.mpUserDetailsService(
									new WechatMpUserDetailsService() {
										@Override
										public UserDetails loadUserByPhone( String phone ) throws UsernameNotFoundException {
											return TtcUser.defaultTest();
										}
									});
							})
						// 小程序登录 同时支持多个小程序
						.wechatMiniAppLogin(miniAppLoginConfigurer -> {
						});
				})
			// **************************************oauth2
			// login登录配置***********************************************
			.with(
				new SocialLoginFilterSecurityConfigurer<HttpSecurity>()
					.socialDelegateClientRegistrationRepository(socialDelegateClientRegistrationRepository),
				( customizer ) -> {
					// 微信网页授权
					customizer
						.wechatWebClient(
							"wxcd395c35c45eb823",
							"75f9a12c82bd24ecac0d37bf1156c749")
						// 企业微信扫码登录
						.workWechatWebLoginClient(
							"wwa70dc5b6e56936e1",
							"nvzGI4Alp3xxxxxxZUc3TtPtKbnfTEets5W8",
							"1000005")
						// 微信扫码登录
						.wechatWebLoginClient(
							"wxcd395c35c45eb823",
							"75f9a12c82bd24ecac0d37bf1156c749");
				})
			// **************************************oauth2表单登录配置***********************************************
			.with(new FormLoginFilterSecurityConfigurer<HttpSecurity>(),
				( customizer ) -> {
					customizer
						.formQrcodeLogin(( formQrcodeLoginHttpConfigurer ) -> {
							formQrcodeLoginHttpConfigurer
								.formQrcodeService(new FormQrcodeService() {
									@Override
									public boolean verifyQrcode( String qrcode ) {
										return true;
									}
								})
								.formQrcodeUserDetailsService(new FormQrcodeUserDetailsService() {
									@Override
									public UserDetails loadUserByPhone( String phone )
										throws UsernameNotFoundException {
										return TtcUser.defaultTest();
									}
								});
						})
						.formSmsLogin(( formSmsLoginHttpConfigurer ) -> {
							formSmsLoginHttpConfigurer
								.formSmsService(new FormSmsService() {
									@Override
									public boolean verifyCaptcha( String phone, String rawCode ) {
										return true;
									}
								})
								.formSmsUserDetailsService(new FormSmsUserDetailsService() {
									@Override
									public UserDetails loadUserByPhone( String phone, String type )
										throws UsernameNotFoundException {
										return TtcUser.defaultTest();
									}
								});
						});

				});
//			.with(new Oauth2FormSmsLoginHttpConfigurer<>(authenticationProperties),
//				Customizer.withDefaults())
//			.with(new OAuth2FormQrcodeLoginHttpConfigurer<>(authenticationProperties),
//				Customizer.withDefaults());

		return httpSecurity
			.addFilterAfter(
				new ExtensionAndOauth2LoginRefreshTokenFilter(oAuth2AccessTokenStore),
				LogoutFilter.class)
			.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}



	@Bean
	public UserDetailsService userDetailsService(
		StrategyUserDetailsService strategyUserDetailsService ) {
		SecurityUserDetailsService securityUserDetailsService =
			new SecurityUserDetailsService(strategyUserDetailsService);
		log.info("Bean  User Details Service] Auto Configure.");
		return securityUserDetailsService;
	}

	@Bean
	public ClientDetailsService clientDetailsService( OAuth2ApplicationService applicationService ) {
		Oauth2ClientDetailsService oauth2ClientDetailsService =
			new Oauth2ClientDetailsService(applicationService);
		log.info("Bean  Client Details Service] Auto Configure.");
		return oauth2ClientDetailsService;
	}

	@Bean
	public SessionRegistry sessionRegistry(
		FindByIndexNameSessionRepository<? extends Session> sessionRepository ) {
		return new SpringSessionBackedSessionRegistry<>(sessionRepository);
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}
}
