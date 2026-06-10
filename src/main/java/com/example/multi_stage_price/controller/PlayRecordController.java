package com.example.multi_stage_price.controller;

import com.example.multi_stage_price.controller.cmd.PlayRecordCmd;
import com.example.multi_stage_price.controller.converter.PlayRecordVOConverter;
import com.example.multi_stage_price.controller.vo.BaseVO;
import com.example.multi_stage_price.controller.vo.MultiPlayRecordVO;
import com.example.multi_stage_price.controller.vo.PlayRecordVO;
import com.example.multi_stage_price.entity.PlayRecord;
import com.example.multi_stage_price.service.PlayRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/play-record")
public class PlayRecordController {
    @Autowired
    private PlayRecordService playRecordService;

    @PostMapping("/insert")
    public BaseVO insert(@RequestBody PlayRecordCmd cmd){
        long startTime = System.currentTimeMillis();
        long endTime;
        try {
            playRecordService.insert(cmd);
            endTime = System.currentTimeMillis();
            return BaseVO.buildBaseVO(200, true, endTime - startTime, null);
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            return BaseVO.buildBaseVO(500, false, endTime - startTime, "其他未知异常");
        }
    }

    @GetMapping("/query")
    public MultiPlayRecordVO queryByUserIdAndTime(int userId, Date startTime, Date endTime){
        long start = System.currentTimeMillis();
        long end;
        MultiPlayRecordVO multiPlayRecordVO = new MultiPlayRecordVO();
        try{
            List<PlayRecord> playRecordList = playRecordService.queryByUserIdAndTime(userId,startTime, endTime);
            List<PlayRecordVO> playRecordVOList = PlayRecordVOConverter.convertList(playRecordList);
            end = System.currentTimeMillis();
            BaseVO baseVO = BaseVO.buildBaseVO(200, true, end - start, null);
            multiPlayRecordVO.setBaseVO(baseVO);
            multiPlayRecordVO.setPlayRecordVOList(playRecordVOList);
            return multiPlayRecordVO;
        } catch (Exception e){
            end = System.currentTimeMillis();
            BaseVO baseVO = BaseVO.buildBaseVO(500, false, end - start, "其他未知异常");
            multiPlayRecordVO.setBaseVO(baseVO);
            return multiPlayRecordVO;
        }
    }
}
