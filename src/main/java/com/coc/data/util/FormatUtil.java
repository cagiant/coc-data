package com.coc.data.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author guokaiqiang
 * @date 2020/9/6 14:02
 */
public class FormatUtil {

    public static String serializeObject2JsonStr(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setTimeZone(TimeZone.getDefault());
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String formatNormalWarTag(Date preparationStartTime, String clanTag1, String clanTag2) {
        String firstTag = clanTag1.compareTo(clanTag2) > 0 ? clanTag1 : clanTag2;
        String secondTag = firstTag.equals(clanTag1) ? clanTag2 : clanTag1;
        return String.format("@%s@%s@%s", firstTag, secondTag, DateUtil.asLocalDate(preparationStartTime).toString());
    }
}
