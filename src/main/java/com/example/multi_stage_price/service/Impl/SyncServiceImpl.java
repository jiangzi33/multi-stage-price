package com.example.multi_stage_price.service.Impl;

import com.example.multi_stage_price.constant.MultiStagePriceConstant;
import com.example.multi_stage_price.controller.cmd.PlayRecordCmd;
import com.example.multi_stage_price.entity.PlayRecord;
import com.example.multi_stage_price.entity.PrizeRecord;
import com.example.multi_stage_price.entity.TotalDuration;
import com.example.multi_stage_price.intergration.SendCoinIntegration;
import com.example.multi_stage_price.intergration.SysConfigIntegration;
import com.example.multi_stage_price.mapper.TotalDurationMapper;
import com.example.multi_stage_price.service.PlayRecordService;
import com.example.multi_stage_price.service.PrizeRecordService;
import com.example.multi_stage_price.service.SyncService;
import com.example.multi_stage_price.util.DateUtil;
import com.example.multi_stage_price.util.JexlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private PrizeRecordService prizeRecordService;
    @Autowired
    private SendCoinIntegration sendCoinIntegration;
    @Override
    //这个方法没有加入transactional的原因：因为就算后面失败了，我们得把流水存下来，为后面的补发提供基础
    public void sync(PlayRecordCmd cmd) {
        cmd.setPrizeCode("Coin_1");
        playRecordService.insert(cmd);
        int total = calculate(cmd);

        saveDuration(cmd,total);

        int stage = calculateStage(total);
        int amount = calculateAmount(total);
        if(stage==0 || amount == 0){
            return;
        }
        try{
            sendPrize(cmd,stage,amount);
        }catch (DuplicateKeyException e){
            log.info("stage = {}, 本阶段已发放", stage);
        }

    }

    private int calculate(PlayRecordCmd cmd){
        List<PlayRecord> playRecordList = playRecordService.queryByUserIdAndTime(cmd.getUserId(), DateUtil.getStartTimeCurrentDate(), DateUtil.getEndTimeCurrentDate());
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

    @Transactional
    protected void sendPrize(PlayRecordCmd cmd, int stage, int amount){
        String outBizNo = cmd.getBizScene() + "_" + cmd.getUserId() + "_" + cmd.getPrizeCode() + "_" + DateUtil.format(new Date()) + "_" + stage + "_" + amount;
        sendCoinIntegration.sendPrice(cmd.getPrizeCode(),amount, outBizNo);
        PrizeRecord prizeRecord = new PrizeRecord();
        prizeRecord.setUserId(cmd.getUserId());
        prizeRecord.setBizScene(cmd.getBizScene());
        prizeRecord.setPrizeCode(cmd.getPrizeCode());
        prizeRecord.setPrizeDate(DateUtil.format(new Date()));
        prizeRecord.setPrizeStage(stage);
        prizeRecord.setPrizeAmount(amount);
        prizeRecord.setOutBizNo(outBizNo);

        prizeRecordService.insert(prizeRecord);
    }
}
