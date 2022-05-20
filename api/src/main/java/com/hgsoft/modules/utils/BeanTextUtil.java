package com.hgsoft.modules.utils;

import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.modules.report.entity.shanxi.EtcOffLineSettlement;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BeanTextUtil {
    /**
     * 获取所有的属性名字
     *
     * @param clazz
     * @param notInclude 函数，判读需要排除那些属性
     * @return
     */
    public static List<String> getAllFieldName(Class<? extends Object> clazz, Function<String, Boolean> notInclude) {
        Field[] declaredFields = clazz.getDeclaredFields();
        ArrayList<String> results = new ArrayList<>(declaredFields.length);
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            if (notInclude.apply(name))
                continue;
            results.add(name);
        }
        return results;
    }

    @Getter
    @AllArgsConstructor
    public static class FieldNameAndSqlType {
        private final String fieldName;
        private final String sqlType;
        private final String comment;
    }

    public static List<FieldNameAndSqlType> getFieldNameAndSqlType(Class<? extends Object> clazz, Function<String, Boolean> notInclude) {
        Field[] declaredFields = clazz.getDeclaredFields();
        ArrayList<FieldNameAndSqlType> results = new ArrayList<>(declaredFields.length);
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            if (notInclude.apply(name))
                continue;
            Class<?> type = declaredField.getType();
            String typeName = type.getSimpleName();
            String sqlType = mapJavaTypeToSqlType(typeName);
            String comment = null;
            if(declaredField.isAnnotationPresent(Excel.class))
                comment = declaredField.getAnnotation(Excel.class).name();
            FieldNameAndSqlType fieldNameAndSqlType = new FieldNameAndSqlType(name, sqlType,comment);
            results.add(fieldNameAndSqlType);
        }
        return results;
    }

    public static String mapJavaTypeToSqlType(String typeName) {
        switch (typeName) {
            case "Integer":
                return "int(11)";
            case "String":
                return "varchar(60)";
            case "Date":
                return "timestamp";
            case "Long":
                return "bigint";
            default:
                return "unknow";
        }
    }

    public static String getFieldsString(Class<? extends Object> clazz, Function<String, String> mapper
            , Function<String, Boolean> notInclude) {
        List<String> allFieldName = getAllFieldName(clazz, notInclude);
        List<String> mapperResults = allFieldName.stream().map(mapper).collect(Collectors.toList());
        String join = String.join(",\n", mapperResults);
        System.out.println(join);
        return join;
    }

    public static String mapBeanToResultMap(Class<? extends Object> clazz, Function<String, Boolean> notInclude) {
        List<String> allFieldName = getAllFieldName(clazz, notInclude);
        String clazzName = clazz.getName();
        StringBuilder builder = new StringBuilder();
        builder.append("<resultMap type=\"").append(clazzName).append("\" id=\"BaseResultMap\" autoMapping=\"true\">\n");
        for (String fieldName : allFieldName) {
            builder.append("<result column=\"" + camelToSnake(fieldName) + "\" property=\"" + fieldName + "\" />\n");
        }
        builder.append("</resultMap>");
        return builder.toString();
    }

    public static String mapBeanToSelectSql(Class<? extends Object> clazz, Function<String, Boolean> notInclude) {
        List<String> allFieldName = getAllFieldName(clazz, notInclude);
        String clazzName = clazz.getSimpleName();
        List<String> collect = allFieldName.stream().map(BeanTextUtil::camelToSnake).collect(Collectors.toList());
        String join = String.join(",\n", collect);
        return "select \n" + join + "\n from \n" + camelToSnake(clazzName);

    }

    public static String mapBeanToVueTable(Class<? extends Object> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();//这里默认了一个条件，需要展示的字段要有@Excel
        List<String> results = new LinkedList<>();
        for (Field field : declaredFields) {
            boolean annotationPresent = field.isAnnotationPresent(Excel.class);
            if (!annotationPresent)
                continue;
            Excel annotation = field.getAnnotation(Excel.class);
            String name = field.getName();
            String chineseName = annotation.name();
            results.add(String.format("{title: '%s', field: '%s', tableField: '%s', minWidth: 180, sortable: false, align: 'center'}", chineseName, name, camelToSnake(name)));
        }
        return String.join(",\n", results);
    }

    public static String mapBeanToCreateTable(Class<? extends Object> clazz, Function<String, Boolean> notInclude) {
        List<FieldNameAndSqlType> fieldNameAndSqlType = getFieldNameAndSqlType(clazz, notInclude);
        String table_name = camelToSnake(clazz.getSimpleName());
        List<String> columns = new LinkedList<>();
        for (FieldNameAndSqlType nameAndSqlType : fieldNameAndSqlType) {
            String fieldName = nameAndSqlType.getFieldName();
            String sqlType = nameAndSqlType.getSqlType();
            String comment = nameAndSqlType.comment;
            if (notInclude.apply(fieldName))
                continue;

            columns.add(String.format("%s %s not null %s %s",
                    camelToSnake(fieldName),
                    sqlType,
                    sqlType.contains("int") ? "default 0" : "",
                    comment != null ? "comment '" + comment + "'" : "comment ''"));
        }
        return String.format("create table %s (\n %s \n);", table_name, String.join(",\n", columns));
    }

    /**
     * 驼峰法变成下划线
     *
     * @param camel
     * @return
     */
    public static String camelToSnake(String camel) {
        if (camel == null || camel.isEmpty())
            return camel;
        //这里应该怎样做捏？
        char[] chars = camel.toCharArray();
        List<Character> result = new LinkedList<>();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (aChar >= 'A' && aChar <= 'Z') {
                if (i > 0)
                    result.add('_');
                result.add((char) (aChar + 32));
            } else
                result.add(aChar);
        }
        char[] chars1 = new char[result.size()];
        for (int i = 0; i < result.size(); i++) {
            chars1[i] = result.get(i);
        }
        return new String(chars1);
    }

    public static void main(String[] args) {
//        String s1 = mapBeanToResultMap(TbLaneHeartBeatAndEexitMonitor.class, (s) -> s.endsWith("Str") || s.startsWith("diff"));
//        System.out.println(s1);
//        String s2 = mapBeanToSelectSql(TbLaneHeartBeatAndEexitMonitor.class, (s) -> s.endsWith("Str") || s.startsWith("diff"));
//        System.out.println(s2);
        System.out.println(mapBeanToVueTable(EtcOffLineSettlement.class));
        //System.out.println(mapBeanToCreateTable(TbLaneHeartBeatAndEexitMonitor.class, (s) -> s.endsWith("Str") || s.startsWith("diff")));
    }
}
