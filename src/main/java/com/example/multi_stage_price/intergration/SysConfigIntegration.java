package com.example.multi_stage_price.intergration;

import com.example.multi_stage_price.exeception.AcrossSysException;
import com.example.multi_stage_price.intergration.vo.SingleSystemConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SysConfigIntegration {
    @Autowired
    private RestTemplate restTemplate;
    public String querySysConfig(String code){
        String url = "http://127.0.0.1:8082/systemConfig/query?code=" + code;
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<SingleSystemConfigVO> singleSystemConfigVOResponseEntity = restTemplate.getForEntity(url, SingleSystemConfigVO.class);
        if(!singleSystemConfigVOResponseEntity.getBody().getBaseVO().isSuccess() || singleSystemConfigVOResponseEntity.getBody().getBaseVO().getCode()!=200){
            throw new AcrossSysException("acrossing system is fail");
        }
        return singleSystemConfigVOResponseEntity.getBody().getSystemConfigVO().getValue();
    }
}
