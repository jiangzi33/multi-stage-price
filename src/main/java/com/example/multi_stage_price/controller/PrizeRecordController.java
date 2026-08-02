package com.example.multi_stage_price.controller;

import com.example.multi_stage_price.controller.cmd.PrizeRecordCmd;
import com.example.multi_stage_price.controller.converter.PrizeRecordVOConverter;
import com.example.multi_stage_price.controller.vo.*;
import com.example.multi_stage_price.entity.PrizeRecord;
import com.example.multi_stage_price.service.PrizeRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/prize-record")
public class PrizeRecordController {
    @Autowired
    private PrizeRecordService prizeRecordService;

    @PostMapping("/insert")
    public BaseVO insert(@RequestBody PrizeRecordCmd cmd){
        long startTime = System.currentTimeMillis();
        long endTime;
        try {
            prizeRecordService.insert(cmd);
            endTime = System.currentTimeMillis();
            return BaseVO.buildBaseVO(200, true, endTime - startTime, null);
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            return BaseVO.buildBaseVO(500, false, endTime - startTime, "其他未知异常");
        }
    }

    @GetMapping("/query")
    public MultiPrizeRecordVO queryByUserIdAndTime(int userId, Date startTime, Date endTime){
        long start = System.currentTimeMillis();
        long end;
        MultiPrizeRecordVO multiPrizeRecordVO = new MultiPrizeRecordVO();
        try{
            List<PrizeRecord> prizeRecordList = prizeRecordService.queryByUserIdAndTime(userId,startTime, endTime);
            List<PrizeRecordVO> prizeRecordVOS = PrizeRecordVOConverter.convertList(prizeRecordList);
            end = System.currentTimeMillis();
            BaseVO baseVO = BaseVO.buildBaseVO(200, true, end - start, null);
            multiPrizeRecordVO.setBaseVO(baseVO);
            multiPrizeRecordVO.setPrizeRecordVOList(prizeRecordVOS);
            return multiPrizeRecordVO;
        } catch (Exception e){
            end = System.currentTimeMillis();
            BaseVO baseVO = BaseVO.buildBaseVO(500, false, end - start, "其他未知异常");
            multiPrizeRecordVO.setBaseVO(baseVO);
            return multiPrizeRecordVO;
        }
    }
}
