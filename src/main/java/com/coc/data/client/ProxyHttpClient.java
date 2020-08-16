package com.coc.data.client;

import com.coc.data.constant.UrlConstants;
import com.coc.data.dto.ClanInfoDTO;
import com.coc.data.dto.WarInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ProxyHttpClient implements HttpClient {

    @Override
    public ClanInfoDTO getClanInfoByTag(String clanTag) {
        return sendPostResult(
                prepareParams(clanTag, UrlConstants.PROXY_OPTION_GET_CLAN_INFO),
                ClanInfoDTO.class
        );
    }

    @Override
    public WarInfoDTO getClanCurrentWarInfoByClanTag(String clanTag) {
        return sendPostResult(
                prepareParams(clanTag, UrlConstants.PROXY_OPTION_GET_CURRENT_WAR_INFO),
                WarInfoDTO.class
        );
    }

    private static <T> T sendPostResult(MultiValueMap<String, String> params, Class tClass) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<T> response = client.exchange(UrlConstants.PROXY_URL, method, requestEntity, tClass);

        return response.getBody();
    }

    private MultiValueMap<String, String> prepareParams(String tag, String option) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.set("tag", tag);
        params.set("option", option);

        return params;
    }
}
