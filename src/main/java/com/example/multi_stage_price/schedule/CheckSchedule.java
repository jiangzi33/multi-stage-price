package com.example.multi_stage_price.schedule;

import com.example.multi_stage_price.entity.PrizeRecord;
import com.example.multi_stage_price.intergration.SendCoinIntegration;
import com.example.multi_stage_price.intergration.vo.CoinRecordVO;
import com.example.multi_stage_price.mapper.PrizeRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class CheckSchedule {
    @Autowired
    private PrizeRecordMapper prizeRecordMapper;
    @Autowired
    private SendCoinIntegration sendCoinIntegration;
    @Scheduled(cron = "0 */1 * * * ?" )
    public void checkPrice(){
        log.info("checking the prize records from {} to {}",new Date(System.currentTimeMillis()-60000),new Date());
        boolean normal = true;
        List<PrizeRecord> prizeRecords = prizeRecordMapper.queryByTime();
        List<CoinRecordVO> coinRecordVOS = sendCoinIntegration.queryCoinRecordList();
        log.info("new records: prize record {}, coin record {}",prizeRecords.size(),coinRecordVOS.size());
        if(prizeRecords.size()!= coinRecordVOS.size()){
            normal = false;
            log.error("the items is not matched from {} to {}",new Date(System.currentTimeMillis()-60000),new Date());
        }
        Map<String, Integer> prizeRecordMap = match(prizeRecords);
        Map<String, Integer> coinRecordMap = match2(coinRecordVOS);

        Set<String> prizeRecordSet = prizeRecordMap.keySet();
        Set<String> coinRecordSet = coinRecordMap.keySet();

        Set<String> tempOldSet = new HashSet<>(prizeRecordSet);
        Set<String> tempNewSet = new HashSet<>(coinRecordSet);

        tempOldSet.removeAll(tempNewSet);
        tempNewSet.removeAll(prizeRecordSet);

        if(!tempOldSet.isEmpty() || !tempNewSet.isEmpty()){
            normal = false;
            log.error("item-key is not same");
        }

        for(String o : prizeRecordMap.keySet()){
            if(!Objects.equals(coinRecordMap.get(o), prizeRecordMap.get(o))){
                normal = false;
                log.error("out-biz-no {} is not correct,prizeRecord is {}, coinRecord is {}", o, prizeRecordMap.get(o), coinRecordMap.get(o));
            }
        }
        if(normal){
            log.info("from {} to {} everything is normal",new Date(System.currentTimeMillis()-60000),new Date());
        }
    }

    private Map<String,Integer> match(List<PrizeRecord> prizeRecords){
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i < prizeRecords.size(); i++) {
            String outBizNo = prizeRecords.get(i).getOutBizNo();
            int amount = prizeRecords.get(i).getPrizeAmount();
            map.put(outBizNo,amount);
        }
        return map;
    }

    private Map<String,Integer> match2(List<CoinRecordVO> coinRecordVOS){
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i < coinRecordVOS.size(); i++) {
            String outBizNo = coinRecordVOS.get(i).getOutBizNo();
            int amount = coinRecordVOS.get(i).getAmount();
            map.put(outBizNo,amount);
        }
        return map;
    }
}
