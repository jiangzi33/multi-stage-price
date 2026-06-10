package com.example.multi_stage_price.controller.converter;

import com.example.multi_stage_price.controller.vo.PlayRecordVO;
import com.example.multi_stage_price.entity.PlayRecord;

import java.util.ArrayList;
import java.util.List;

public class PlayRecordVOConverter {
    public static PlayRecordVO convert(PlayRecord playRecord){
        if (playRecord == null) {

            return null;

        }

        PlayRecordVO vo = new PlayRecordVO();

        vo.setId(playRecord.getId());

        vo.setUserId(playRecord.getUserId());

        vo.setSoundId(playRecord.getSoundId());

        vo.setDuration(playRecord.getDuration());

        vo.setSyncTime(playRecord.getSyncTime());

        vo.setCreateTime(playRecord.getCreateTime());

        vo.setUpdateTime(playRecord.getUpdateTime());

        return vo;
    }

    public static List<PlayRecordVO> convertList(List<PlayRecord> playRecordList){
        if (playRecordList == null || playRecordList.isEmpty()) {

            return new ArrayList<>();

        }

        List<PlayRecordVO> result = new ArrayList<>(playRecordList.size());

        for (PlayRecord playRecord : playRecordList) {

            result.add(convert(playRecord));

        }

        return result;
    }
}
