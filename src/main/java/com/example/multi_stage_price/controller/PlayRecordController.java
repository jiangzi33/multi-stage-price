package com.example.multi_stage_price.controller;

import com.example.multi_stage_price.controller.cmd.PlayRecordCmd;
import com.example.multi_stage_price.controller.converter.PlayRecordVOConverter;
import com.example.multi_stage_price.controller.vo.BaseVO;
import com.example.multi_stage_price.controller.vo.MultiPlayRecordVO;
import com.example.multi_stage_price.controller.vo.PlayRecordVO;
import com.example.multi_stage_price.entity.PlayRecord;
import com.example.multi_stage_price.intergration.MusicIntegration;
import com.example.multi_stage_price.service.PlayRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/play-record")
public class PlayRecordController {
    @Autowired
    private PlayRecordService playRecordService;
    @Autowired
    private MusicIntegration musicIntegration;

    @GetMapping("/query")
    public MultiPlayRecordVO queryByUserIdAndTime(int userId, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                                  @RequestParam(defaultValue = "0") int pageStart, @RequestParam(defaultValue = "10") int pageSize){
        long start = System.currentTimeMillis();
        long end;
        MultiPlayRecordVO multiPlayRecordVO = new MultiPlayRecordVO();
        try{
            List<PlayRecord> playRecordList = playRecordService.queryByUserIdAndTime(userId, startTime, endTime, pageStart, pageSize);
            List<PlayRecordVO> playRecordVOList = PlayRecordVOConverter.convertList(playRecordList);
            for (PlayRecordVO playRecordVO : playRecordVOList) {
                playRecordVO.setSoundName(musicIntegration.queryTitleById(playRecordVO.getSoundId()));
            }
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
