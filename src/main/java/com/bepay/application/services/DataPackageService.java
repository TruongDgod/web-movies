package com.bepay.application.services;

import com.bepay.application.dto.CheckNameDto;
import com.bepay.application.dto.CodeDTO;
import com.bepay.application.dto.RoleDTO;
import com.bepay.application.dto.ServiceGroupDTO;
import com.bepay.application.dto.request.*;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.dto.response.DataPackageResponseDetail;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface DataPackageService extends AbstractService{
    public BaseResponse search(DataPackageRequest request);
    public List<RoleDTO> getRole(BigDecimal groupId);
    public List<ServiceGroupDTO> getGroup(DataPackageGetRoleRequest request);
    public BaseResponse add(DataPackageAddRequest request);
    public DataPackageResponseDetail detail(SearchDetailRequest request);
    public BaseResponse update(DataPackageAddRequest request);
    public void delete(SearchDetailRequest request);
    public BaseResponse getStatus();
    public BaseResponse searchPackage(DataPackageActionRequest request);
    public BaseResponse detailAction(PromotionActionRequest  request);
    public BaseResponse check(CodeDTO request);
//    BaseResponse checkName(CheckNameDto request);
}
