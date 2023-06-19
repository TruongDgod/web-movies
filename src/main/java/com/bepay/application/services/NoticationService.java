package com.bepay.application.services;

import com.bepay.application.dto.request.NotificationRequestDTO;
import com.bepay.application.dto.request.SearchDetailRequest;
import com.bepay.application.dto.request.SearchRequest;
import com.bepay.application.dto.response.BaseResponse;
import com.bepay.application.dto.response.NotificationResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;

@Service
public interface NoticationService extends AbstractService {
    public BaseResponse search(SearchRequest request);
    public BaseResponse add(NotificationRequestDTO request) throws ParseException, IOException;
    public NotificationResponseDTO getDetail(SearchDetailRequest request);
    public BaseResponse update(NotificationRequestDTO request) throws IOException, ParseException;
    public void delete(SearchDetailRequest request);
    public BigDecimal saveUserList(MultipartFile request, BigDecimal messageId);
    public BaseResponse copy(NotificationRequestDTO request);
    public BaseResponse push(SearchDetailRequest request);
    public BaseResponse checkFile(MultipartFile request);
}
