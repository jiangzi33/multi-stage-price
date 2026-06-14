package com.example.multi_stage_price.service;

import com.example.multi_stage_price.controller.cmd.PlayRecordCmd;
import com.example.multi_stage_price.entity.PlayRecord;

import java.util.Date;
import java.util.List;

public interface PrizeRecordService {
    void insert(PlayRecordCmd cmd);
    List<PlayRecord> queryByUserIdAndTime(int userId, Date startTime, Date endTime);
}
