package com.example.multi_stage_price.service.Impl;

import com.example.multi_stage_price.controller.cmd.PlayRecordCmd;
import com.example.multi_stage_price.entity.PlayRecord;
import com.example.multi_stage_price.service.PrizeRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PrizeRecordServiceImpl implements PrizeRecordService {
    @Override
    public void insert(PlayRecordCmd cmd) {

    }

    @Override
    public List<PlayRecord> queryByUserIdAndTime(int userId, Date startTime, Date endTime) {
        return List.of();
    }
}
