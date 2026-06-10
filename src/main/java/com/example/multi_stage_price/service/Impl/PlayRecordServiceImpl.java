package com.example.multi_stage_price.service.Impl;

import com.example.multi_stage_price.controller.cmd.PlayRecordCmd;
import com.example.multi_stage_price.entity.PlayRecord;
import com.example.multi_stage_price.mapper.PlayRecordMapper;
import com.example.multi_stage_price.service.PlayRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlayRecordServiceImpl implements PlayRecordService {
    @Autowired
    private PlayRecordMapper playRecordMapper;
    @Override
    public void insert(PlayRecordCmd cmd) {
        PlayRecord playRecord = buildPlayRecord(cmd);
        playRecordMapper.insert(playRecord);
    }

    @Override
    public List<PlayRecord> queryByUserIdAndTime(int userId, Date startTime, Date endTime) {
       return playRecordMapper.queryByUserIdAndTime(userId,startTime,endTime);
    }

    private PlayRecord buildPlayRecord(PlayRecordCmd cmd){
        PlayRecord playRecord = new PlayRecord();
        playRecord.setUserId(cmd.getUserId());
        playRecord.setSoundId(cmd.getSoundId());
        playRecord.setDuration(cmd.getDuration());

        return playRecord;
    }
}
