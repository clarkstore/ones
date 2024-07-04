package com.ones.common.web.config;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.ones.common.web.constant.OsDesensitizationTypeEnum;
import com.ones.common.web.annotation.OsDesensitization;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

/**
 * 脱敏序列化器
 * @author Clark
 * @version 2023-02-27
 */
@NoArgsConstructor
@AllArgsConstructor
public class OsDesensitizationSerializer extends JsonSerializer<String> implements ContextualSerializer {
    // 脱敏类型
    private OsDesensitizationTypeEnum desensitizationType;
    // 前几位不脱敏
    private Integer prefixNoMaskLen;
    // 最后几位不脱敏
    private Integer suffixNoMaskLen;

    @Override
    public void serialize(final String origin, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        if (StrUtil.isNotBlank(origin) && null != desensitizationType) {
            switch (desensitizationType) {
                case CUSTOMER:
                    jsonGenerator.writeString(StrUtil.hide(origin, prefixNoMaskLen, suffixNoMaskLen));
                    break;
                case USER_ID:
                    jsonGenerator.writeString(DesensitizedUtil.userId().toString());
                    break;
                case CHINESE_NAME:
                    jsonGenerator.writeString(DesensitizedUtil.chineseName(origin));
                    break;
                case ID_CARD:
                    jsonGenerator.writeString(DesensitizedUtil.idCardNum(origin, 3, 2));
                    break;
                case FIXED_PHONE:
                    jsonGenerator.writeString(DesensitizedUtil.fixedPhone(origin));
                    break;
                case MOBILE_PHONE:
                    jsonGenerator.writeString(DesensitizedUtil.mobilePhone(origin));
                    break;
                case ADDRESS:
                    jsonGenerator.writeString(DesensitizedUtil.address(origin, 8));
                    break;
                case EMAIL:
                    jsonGenerator.writeString(DesensitizedUtil.email(origin));
                    break;
                case PASSWORD:
                    jsonGenerator.writeString(DesensitizedUtil.password(origin));
                    break;
                case CAR_LICENSE:
                    jsonGenerator.writeString(DesensitizedUtil.carLicense(origin));
                    break;
                case BANK_CARD:
                    jsonGenerator.writeString(DesensitizedUtil.bankCard(origin));
                    break;
                case IPV4:
                    jsonGenerator.writeString(DesensitizedUtil.ipv4(origin));
                    break;
                case IPV6:
                    jsonGenerator.writeString(DesensitizedUtil.ipv6(origin));
                    break;
                default:
                    throw new IllegalArgumentException("unknown privacy type enum " + desensitizationType);
            }
        }
    }

    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider,
                                              final BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                OsDesensitization annotation = beanProperty.getAnnotation(OsDesensitization.class);
                if (annotation == null) {
                    annotation = beanProperty.getContextAnnotation(OsDesensitization.class);
                }
                if (annotation != null) {
                    return new OsDesensitizationSerializer(annotation.type(), annotation.prefixNoMaskLen(),
                            annotation.suffixNoMaskLen());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }
}