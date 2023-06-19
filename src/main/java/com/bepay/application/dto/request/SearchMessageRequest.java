package com.bepay.application.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchMessageRequest extends BaseRequest {
    String content;
}
