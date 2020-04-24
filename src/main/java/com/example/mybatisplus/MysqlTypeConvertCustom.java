package com.example.mybatisplus;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

/**
 * mysql对应java数据类型转换
 * tinyint对应integer
 * datetime对应date
 */
public class MysqlTypeConvertCustom extends MySqlTypeConvert implements ITypeConvert {

    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        String t = fieldType.toLowerCase();
        if (t.contains("tinyint(1)")) {
            return DbColumnType.INTEGER;
        }
        if (t.contains("datetime")) {
            return DbColumnType.DATE;
        }
        return super.processTypeConvert(globalConfig, fieldType);
    }
}
