<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

    <#if enableCache>
        <!-- 开启二级缓存 -->
        <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

    </#if>
    <#if baseResultMap>
        <!-- 通用查询映射结果 -->
        <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
            <#list table.fields as field>
                <#if field.keyFlag><#--生成主键排在第一位-->
                    <#if '${field.type?index_of("(")}'?number == -1>
                        <#assign typeLength='${field.type?length}'?number />
                    <#else>
                        <#assign typeLength='${field.type?index_of("(")}'?number />
                    </#if>
                    <id column="${field.name}" property="${field.propertyName}"
                        jdbcType="${(field.type?substring(0,typeLength))?upper_case}"/>
                </#if>
            </#list>
            <#list table.commonFields as field><#--生成公共字段 -->
                <#if '${field.type?index_of("(")}'?number == -1>
                    <#assign typeLength='${field.type?length}'?number />
                <#else>
                    <#assign typeLength='${field.type?index_of("(")}'?number />
                </#if>
                <result column="${field.name}" property="${field.propertyName}"
                        jdbcType="${(field.type?substring(0,typeLength))?upper_case}"/>
            </#list>
            <#list table.fields as field>
                <#if !field.keyFlag><#--生成普通字段 -->
                    <#if '${field.type?index_of("(")}'?number == -1>
                        <#assign typeLength='${field.type?length}'?number />
                    <#else>
                        <#assign typeLength='${field.type?index_of("(")}'?number />
                    </#if>
                    <result column="${field.name}" property="${field.propertyName}"
                            jdbcType="${(field.type?substring(0,typeLength))?upper_case}"/>
                </#if>
            </#list>
        </resultMap>

    </#if>
    <#if baseColumnList>
        <!-- 通用查询结果列 -->
        <sql id="Base_Column_List">
            <#list table.commonFields as field>
                ${field.name},
            </#list>
            ${table.fieldNames}
        </sql>

    </#if>
</mapper>
