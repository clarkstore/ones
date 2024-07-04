/*
 *
 *  * Copyright (c) 2021 os-parent Authors. All Rights Reserved.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.ones.kit.codegun.util;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.stereotype.Component;

/**
 * 代码生成器：基于3.5.X新版
 * @author Clark
 * @version 2022-04-29
 */
@Slf4j
@Component
public class OsCodeGenUtils {
    /**
     * 获取配置信息
     *
     * @return Configuration
     */
    public Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            log.error("获取配置文件失败，", e);
        }
        return null;
    }

    /**
     * 生成代码
     */
    public void codeGenerator() {
        Configuration config = getConfig();

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig.Builder(config.getString("Db.Url"),config.getString("Db.Username"),config.getString("Db.Password"))
                .dbQuery(new MySqlQuery())
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler()).build();
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator(dsc);
        String projectPath = System.getProperty("user.dir");
        // 全局配置
        GlobalConfig gc = new GlobalConfig.Builder()
                .outputDir(projectPath + config.getString("OutputDir"))
                .author(config.getString("Author"))
                .dateType(DateType.TIME_PACK)
                .commentDate("yyyy-MM-dd")
                .build();
        mpg.global(gc);

        // 包配置
        PackageConfig pc = new PackageConfig.Builder()
                .parent(config.getString("PackageConfig.Parent"))
                .moduleName(config.getString("PackageConfig.ModuleName"))
                .entity(config.getString("PackageConfig.Entity"))
                .controller(config.getString("PackageConfig.Controller"))
//                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://"))
                .build();
        mpg.packageInfo(pc);

        //配置自定义模板
        TemplateConfig tc = new TemplateConfig.Builder()
                .controller("/templates/api.java")
                .build();
        mpg.template(tc);

        // 策略配置
        StrategyConfig sc = new StrategyConfig.Builder()
                .enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
                .addTablePrefix(config.getStringArray("TablePrefix"))
                // Entity策略配置
                .entityBuilder().enableFileOverride()
                .disableSerialVersionUID()
                .enableLombok()
                .enableTableFieldAnnotation()
                .enableActiveRecord()
                .versionColumnName("version")
                .logicDeleteColumnName("deleted")
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .addTableFills(new Column("create_time", FieldFill.INSERT))
                .addTableFills(new Column("update_time", FieldFill.UPDATE))
                .idType(IdType.ASSIGN_ID)
                // Controller策略配置
                .controllerBuilder().enableFileOverride()
                .enableRestStyle()
                .formatFileName("%sApi")
                .mapperBuilder()
                .superClass(BaseMapper.class)
                .enableMapperAnnotation()
                .enableBaseResultMap()
                .enableBaseColumnList()
                // service策略配置
                .serviceBuilder().enableFileOverride()
                // mapper策略配置
                .mapperBuilder().enableFileOverride()
                .build();
        mpg.strategy(sc);

        mpg.execute();
    }
}