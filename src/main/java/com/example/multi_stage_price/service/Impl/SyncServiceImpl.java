package com.example.multi_stage_price.service.Impl;

import com.example.multi_stage_price.constant.MultiStagePriceConstant;
import com.example.multi_stage_price.controller.cmd.PlayRecordCmd;
import com.example.multi_stage_price.entity.PlayRecord;
import com.example.multi_stage_price.entity.TotalDuration;
import com.example.multi_stage_price.intergration.SysConfigIntegration;
import com.example.multi_stage_price.mapper.TotalDurationMapper;
import com.example.multi_stage_price.service.PlayRecordService;
import com.example.multi_stage_price.service.SyncService;
import com.example.multi_stage_price.util.DateUtil;
import com.example.multi_stage_price.util.JexlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SyncServiceImpl implements SyncService {
    @Autowired
    private PlayRecordService playRecordService;
    @Autowired
    private SysConfigIntegration sysConfigIntegration;
    @Autowired
    private TotalDurationMapper totalDurationMapper;
    @Override
    public void sync(PlayRecordCmd cmd) {
        playRecordService.insert(cmd);
        List<PlayRecord> playRecordList = playRecordService.queryByUserIdAndTime(cmd.getUserId(), DateUtil.getStartTimeCurrentDate(), DateUtil.getEndTimeCurrentDate());
        int total = calculate(playRecordList);

        saveDuration(cmd,total);

        int stage = calculateStage(total);
        int amount = calculateAmount(total);
    }

    private int calculate(List<PlayRecord> playRecordList){
        int totalDuration = 0;
        for (int i = 0; i < playRecordList.size(); i++) {
            int time = playRecordList.get(i).getDuration();
            totalDuration += time;
        }
        return totalDuration;
    }

    private void saveDuration(PlayRecordCmd cmd, int totalDuration){
        TotalDuration durationInDB = totalDurationMapper.queryByUserIdAndDate(cmd.getUserId(), DateUtil.format(new Date()));
        if(durationInDB==null){
            TotalDuration duration = new TotalDuration();
            duration.setTotalDuration(totalDuration);
            duration.setDate(DateUtil.format(new Date()));
            duration.setUserId(cmd.getUserId());
            totalDurationMapper.insert(duration);
        } else {
            durationInDB.setTotalDuration(totalDuration);
            totalDurationMapper.modify(durationInDB);
        }
    }

    private int calculateStage(int totalDuration){
        String stageRule = sysConfigIntegration.querySysConfig(MultiStagePriceConstant.PRICE_STAGE_RULE_CODE);
        log.info("stage rule: {}",stageRule);
        int stage = JexlUtil.getStage(stageRule,totalDuration);
        log.info("stage level: {}",stage);
        return stage;
    }

    private int calculateAmount(int totalDuration){
        String amountRule = sysConfigIntegration.querySysConfig(MultiStagePriceConstant.PRICE_AMOUNT_RULE_CODE);
        log.info("amount rule: {}", amountRule);
        int amount = JexlUtil.getAmount(amountRule,totalDuration);
        log.info("amount: {}", amount);
        return amount;
    }
}
