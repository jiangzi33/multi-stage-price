package com.example.multi_stage_price.intergration;

import com.example.multi_stage_price.controller.vo.BaseVO;
import com.example.multi_stage_price.exeception.SendPriceFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class SendCoinIntegration {
    @Autowired
    private RestTemplate restTemplate;

    public void sendPrice(String code, int amount,String outBizNo){
        try {
            String url = "http://127.0.0.1:8084/coin/decrease-store?code=" + code + "&amount=" + amount + "&outBizNo=" + outBizNo;
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Object> requestEntity = new HttpEntity<>(null,headers);
            ResponseEntity<BaseVO> result = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, BaseVO.class);
            if (result == null || result.getBody()==null || !result.getBody().isSuccess()) {
                throw new SendPriceFailException("fail to send price");
            }
        } catch (Exception e) {
            log.warn("fail to send price, soundCode={}, amount={}, outBizNo={}", code, amount, outBizNo);
        }
    }
}
