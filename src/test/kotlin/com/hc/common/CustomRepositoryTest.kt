package com.hc.common

import com.hc.shared.config.QuerydslConfig
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@DataJpaTest
@Import(QuerydslConfig::class)
@ActiveProfiles("test")
@ComponentScan(basePackages = ["com.hc"])
annotation class CustomRepositoryTest
