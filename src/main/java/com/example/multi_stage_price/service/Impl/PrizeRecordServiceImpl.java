package com.example.multi_stage_price.service.Impl;

import com.example.multi_stage_price.controller.cmd.PlayRecordCmd;
import com.example.multi_stage_price.controller.cmd.PrizeRecordCmd;
import com.example.multi_stage_price.entity.PlayRecord;
import com.example.multi_stage_price.entity.PrizeRecord;
import com.example.multi_stage_price.mapper.PrizeRecordMapper;
import com.example.multi_stage_price.service.PrizeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PrizeRecordServiceImpl implements PrizeRecordService {
    @Autowired
    private PrizeRecordMapper prizeRecordMapper;
    @Override
    public void insert(PrizeRecordCmd cmd) {
        PrizeRecord prizeRecord = buildPrizeRecord(cmd);
        prizeRecordMapper.insert(prizeRecord);
    }

    @Override
    public List<PrizeRecord> queryByUserIdAndTime(int userId, Date startTime, Date endTime) {
        return prizeRecordMapper.queryByUserIdAndTime(userId,startTime,endTime);
    }

    private PrizeRecord buildPrizeRecord(PrizeRecordCmd cmd) {
        PrizeRecord prizeRecord = new PrizeRecord();

        prizeRecord.setUserId(cmd.getUserId());
        prizeRecord.setBizScene(cmd.getBizScene());
        prizeRecord.setPrizeCode(cmd.getPrizeCode());
        prizeRecord.setPrizeDate(cmd.getPrizeDate());
        prizeRecord.setPrizeStage(cmd.getPrizeStage());
        prizeRecord.setPrizeAmount(cmd.getPrizeAmount());

        String outBizNo = cmd.getBizScene() + "_" + cmd.getUserId() + "_" + cmd.getPrizeCode() + "_" + cmd.getPrizeDate() + "_" + cmd.getPrizeStage() + "_" + cmd.getPrizeAmount();
        prizeRecord.setOutBizNo(outBizNo);

        return prizeRecord;
    }
}
