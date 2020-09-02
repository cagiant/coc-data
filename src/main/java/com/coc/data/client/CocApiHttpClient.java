package com.coc.data.client;

import com.coc.data.constant.UrlConstants;
import com.coc.data.dto.ClanInfoDTO;
import com.coc.data.dto.WarInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * 使用官方api获取数据
 * @author guokaiqiang
 * @date 2020/7/30 22:16
 */
@Slf4j
@Component
public class CocApiHttpClient implements HttpClient {

    private final WebClient webClient;

    public CocApiHttpClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
            .filter((req, next) -> {
                final ClientRequest.Builder request = ClientRequest.from(req);
                request.headers( httpHeaders -> {httpHeaders.setBearerAuth(UrlConstants.BEAR_TOKEN);});
                return next.exchange(request.build());
            })
            .baseUrl(UrlConstants.BASE_COC_URL).build();
    }

    /**
     * 根据 clan tag 请求 clan info api。
     * @param clanTag
     * @return java.util.List<com.coc.data.dto.ClanInfoDTO>
     * @author guokaiqiang
     * @date 2020/8/1 10:35
     **/
    @Override
    public ClanInfoDTO getClanInfoByTag(String clanTag) {
        Flux<ClanInfoDTO> clanInfoDTOFlux = this.webClient.get().uri(UrlConstants.CLAN_INFO, clanTag)
            .retrieve().bodyToFlux(ClanInfoDTO.class);

        return clanInfoDTOFlux.singleOrEmpty().block();
    }

    /**
     * 请求当前战争api
     * @param
     * @return com.coc.data.dto.ClanWarInfoDTO
     * @author guokaiqiang
     * @date 2020/8/1 17:21
     **/
    @Override
    public WarInfoDTO getClanCurrentWarInfoByClanTag(String clanTag) {
        Flux<WarInfoDTO> clanWarInfoDTOFlux = this.webClient.get().uri(UrlConstants.CLAN_WAR_INFO, clanTag)
            .retrieve().bodyToFlux(WarInfoDTO.class);

        return clanWarInfoDTOFlux.singleOrEmpty().block();
    }
}