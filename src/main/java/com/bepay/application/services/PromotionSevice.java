package com.bepay.application.services;

import com.bepay.application.dto.request.PromotionRequestDTO;
import com.bepay.application.dto.request.SearchDetailRequest;
import com.bepay.application.dto.request.SearchRequest;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.dto.response.PromotionResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface PromotionSevice {
    public BaseResponse add(PromotionRequestDTO dto);
    public BaseResponse search(SearchRequest request);
    public BaseResponse update(PromotionRequestDTO dto);
    public PromotionResponseDTO getDetail(SearchDetailRequest request);
    public void delete(SearchDetailRequest request);
    public BaseResponse getFeature();

}
