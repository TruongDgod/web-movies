package com.bepay.application.services;

import com.bepay.application.dto.CheckNameDto;
import com.bepay.application.dto.CodeDTO;
import com.bepay.application.dto.request.DataGroupRequestDTO;
import com.bepay.application.dto.request.DataGroupSearchRequest;
import com.bepay.application.dto.request.PromotionActionRequest;
import com.bepay.application.dto.request.SearchDetailRequest;
import com.bepay.application.dto.response.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface DataGroupService extends AbstractService{
    public BaseResponse search(DataGroupSearchRequest request);
    public BaseResponse add(DataGroupRequestDTO request);
    public DataGroupRequestDTO getDetail(SearchDetailRequest request);
    public void delete(SearchDetailRequest request);
    public BaseResponse update(DataGroupRequestDTO request);
    public BaseResponse getStatus();
    public BaseResponse getRole();
    public BaseResponse searchPackage(PromotionActionRequest request);
    public BaseResponse listAction();
    public BaseResponse check(CodeDTO request);
//    public BaseResponse checkName(CheckNameDto request);
}
