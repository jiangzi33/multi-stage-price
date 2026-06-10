package com.example.multi_stage_price.service.Impl;

import com.example.multi_stage_price.controller.cmd.PlayRecordCmd;
import com.example.multi_stage_price.entity.PlayRecord;
import com.example.multi_stage_price.service.PlayRecordService;
import com.example.multi_stage_price.service.SyncService;
import com.example.multi_stage_price.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncServiceImpl implements SyncService {
    @Autowired
    private PlayRecordService playRecordService;
    @Override
    public void sync(PlayRecordCmd cmd) {
        playRecordService.insert(cmd);
        List<PlayRecord> playRecordList = playRecordService.queryByUserIdAndTime(cmd.getUserId(), DateUtil.getStartTimeCurrentDate(), DateUtil.getEndTimeCurrentDate());
        int totalDuration = 0;
        for (int i = 0; i < playRecordList.size(); i++) {
            int time = playRecordList.get(i).getDuration();
            totalDuration += time;
        }
    }
}
