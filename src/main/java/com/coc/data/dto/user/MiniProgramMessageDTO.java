package com.coc.data.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author kqguo@leqee.com
 * @date 2021/6/30 12:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniProgramMessageDTO {

    @JsonProperty(value = "touser")
    private String openId;

    @JsonProperty(value = "template_id")
    private String templateId;

    private String page;

    private String lang;

    private Map<String, TemplateData> data;

    public static class TemplateData {

        private String value;

        public TemplateData(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
