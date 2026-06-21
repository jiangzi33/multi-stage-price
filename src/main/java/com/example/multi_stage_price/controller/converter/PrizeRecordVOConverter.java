package com.example.multi_stage_price.controller.converter;

import com.example.multi_stage_price.controller.vo.PrizeRecordVO;
import com.example.multi_stage_price.entity.PrizeRecord;

import java.util.ArrayList;
import java.util.List;

public class PrizeRecordVOConverter {

    public static PrizeRecordVO convert(PrizeRecord prizeRecord) {
        if (prizeRecord == null) {
            return null;
        }

        PrizeRecordVO vo = new PrizeRecordVO();

        vo.setId(prizeRecord.getId());
        vo.setUserId(prizeRecord.getUserId());
        vo.setBizScene(prizeRecord.getBizScene());
        vo.setPrizeCode(prizeRecord.getPrizeCode());
        vo.setPrizeDate(prizeRecord.getPrizeDate());
        vo.setPrizeStage(prizeRecord.getPrizeStage());
        vo.setPrizeAmount(prizeRecord.getPrizeAmount());
        vo.setCreateTime(prizeRecord.getCreateTime());
        vo.setUpdateTime(prizeRecord.getUpdateTime());

        return vo;
    }

    public static List<PrizeRecordVO> convertList(List<PrizeRecord> prizeRecordList) {
        if (prizeRecordList == null || prizeRecordList.isEmpty()) {
            return new ArrayList<>();
        }

        List<PrizeRecordVO> result = new ArrayList<>(prizeRecordList.size());

        for (PrizeRecord prizeRecord : prizeRecordList) {
            result.add(convert(prizeRecord));
        }

        return result;
    }
}

