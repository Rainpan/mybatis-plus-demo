package com.example.mybatisplus;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class Generator {

    private String driveName,username,password,url;
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

    public void generator(){
        //全局
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor(author)
                .setOutputDir("src/main/java")
                .setOpen(false)
                .setFileOverride(true)
                .setActiveRecord(false)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setEntityName("%sDO");

        //数据源
        DataSourceConfig ds = new DataSourceConfig();
        ds.setDbType(DbType.MYSQL)
                .setDriverName(driveName)
                .setUsername(username)
                .setPassword(password)
                .setUrl(url);

        //数据表
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel)
                .setEntityBuilderModel(true)
                .setEntityLombokModel(true)
                .setInclude(tables);

        //包
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageName);

        AutoGenerator ag = new AutoGenerator();
        ag.setTemplateEngine(new FreemarkerTemplateEngine())
                .setGlobalConfig(gc)
                .setDataSource(ds)
                .setStrategy(strategy)
                .setPackageInfo(pc)
                .execute();
    }
}
