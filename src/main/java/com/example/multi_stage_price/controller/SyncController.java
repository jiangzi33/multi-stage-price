package com.example.multi_stage_price.controller;

import com.example.multi_stage_price.controller.cmd.PlayRecordCmd;
import com.example.multi_stage_price.controller.vo.BaseVO;
import com.example.multi_stage_price.service.SyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/record")
public class SyncController {
    @Autowired
    private SyncService syncService;

    @PostMapping("/sync")
    public BaseVO sync(PlayRecordCmd cmd) {
        long startTime = System.currentTimeMillis();
        long endTime;
        try {
            syncService.sync(cmd);
            endTime = System.currentTimeMillis();
            return BaseVO.buildBaseVO(200, true, endTime - startTime, null);
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            return BaseVO.buildBaseVO(500, false, endTime - startTime, "其他未知异常");
        }
    }
}
