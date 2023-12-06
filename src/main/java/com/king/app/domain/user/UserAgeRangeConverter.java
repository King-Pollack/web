package com.king.app.domain.user;

import com.king.app.domain.type.UserAgeRange;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter
public class UserAgeRangeConverter implements AttributeConverter<UserAgeRange, String> {
    @Override
    public String convertToDatabaseColumn(UserAgeRange attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public UserAgeRange convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return UserAgeRange.of(dbData);
    }
}