package com.example.mybatisplus;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * mybatis plus 自动生成代码
 * <p>
 * -- 1.即使EntityTableFieldAnnotationEnable设置为false,下列情况仍会设置为true,
 * --  1) CaptitalMode设置了true,但表字段名为小写
 * --  2) columnNaming设置为underline_to_camel,并且字段名包含大写
 * --  3) 字段名与设置的columnNaming模式转换后的字段名不相等
 */
public class Generator {

    private String driveName, username, password, url;
    private String[] tables;
    private String packageName;
    private String author;

    public Generator() throws IOException {
        Resource resource = new ClassPathResource("application.properties");
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);
        driveName = properties.getProperty("db.drive.name");
        username = properties.getProperty("db.username");
        password = properties.getProperty("db.password");
        url = properties.getProperty("db.url");
        tables = properties.getProperty("tables.name").split(",");
        packageName = properties.getProperty("package.name");
        author = properties.getProperty("author");
    }

    public static void main(String[] args) throws IOException {
        new Generator().generator();
    }

    public void generator() {
        //全局
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor(author)
                .setOutputDir("src/main/java")
                .setFileOverride(true)
                .setActiveRecord(false)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setDateType(DateType.ONLY_DATE)
                .setEntityName("%sDO")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl");

        //数据源
        DataSourceConfig ds = new DataSourceConfig();
        ds.setDbType(DbType.MYSQL)
                .setDriverName(driveName)
                .setUsername(username)
                .setPassword(password)
                .setUrl(url)
                .setTypeConvert(new MysqlTypeConvertCustom());

        //数据表
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel)
                .setEntityBuilderModel(true)
                .setEntityLombokModel(true)
                .setEntityTableFieldAnnotationEnable(false)
                .setEntitySerialVersionUID(true)
                .setInclude(tables);

        //包
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageName);

        // 模板
        TemplateConfig tc = new TemplateConfig();
        tc.setController(null)
                .setEntity("template/entity.java")
                .setXml("template/mapper.xml");

        AutoGenerator ag = new AutoGenerator();
        ag.setTemplateEngine(new FreemarkerTemplateEngine())
                .setGlobalConfig(gc)
                .setDataSource(ds)
                .setStrategy(strategy)
                .setPackageInfo(pc)
                .setTemplate(tc)
                .execute();
    }
}
