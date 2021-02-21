package com.favoritemedium.sttops.model.converter;

import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Converter(autoApply = true)
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, Timestamp> {

    private static ZoneId utcZoneId = ZoneId.of("UTC");

    @Override
    public Timestamp convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
        return (zonedDateTime == null ? null :
                Timestamp.valueOf(zonedDateTime.withZoneSameInstant(utcZoneId).toLocalDateTime()));
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
        return (sqlTimestamp == null ? null :
                sqlTimestamp.toLocalDateTime().atZone(LocaleContextHolder.getTimeZone().toZoneId()));
    }
}