package com.coc.data.util;

import com.coc.data.dto.LeagueGroupClanInfoDTO;
import com.coc.data.dto.LeagueGroupInfoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.collect.Lists;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

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
        List<String> clanTags = Lists.newArrayList(Arrays.asList(clanTag1, clanTag2));
        clanTags = clanTags.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        return String.format("@%s@%s@%s", clanTags.get(0), clanTags.get(1), DateUtil.asLocalDate(preparationStartTime).toString());
    }

    public static String formatLeagueTag(LeagueGroupInfoDTO leagueGroupInfo) {
        List<String> clanTags =
            leagueGroupInfo.getClans().stream().map(LeagueGroupClanInfoDTO::getTag).collect(Collectors.toList());
        clanTags = clanTags.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        String stringToHash = String.format("%s,%s", String.join(",", clanTags), leagueGroupInfo.getSeason());
        try {
            // 创建一个MessageDigest实例:
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 反复调用update输入数据:
            md.update(stringToHash.getBytes("UTF-8"));
            byte[] result = md.digest();
            return new BigInteger(1, result).toString(16);
        } catch (Exception e) {
            return String.format("%s-%s", clanTags.get(0), leagueGroupInfo.getSeason());
        }
    }

    /**
     * 驼峰json转obj
     * @param jsonStr
     * @param tClz
     * @return T
     * @author guokaiqiang
     * @date 2020/11/19 10:43
     **/
    public static <T> T deserializeCamelCaseJson2Object(String jsonStr, Class<T> tClz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(jsonStr, tClz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
