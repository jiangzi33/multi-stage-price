package com.example.multi_stage_price.intergration;

import com.example.multi_stage_price.intergration.vo.SingleMusicVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class MusicIntegration {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 根据 soundId 查询歌曲名称，查询失败时返回 null（不阻断播放记录列表）。
     */
    public String queryTitleById(int id) {
        try {
            String url = "http://127.0.0.1:8082/music/id?id=" + id;
            ResponseEntity<SingleMusicVO> responseEntity = restTemplate.getForEntity(url, SingleMusicVO.class);
            SingleMusicVO body = responseEntity.getBody();
            if (body == null || body.getBaseVO() == null
                    || !body.getBaseVO().isSuccess() || body.getBaseVO().getCode() != 200
                    || body.getMusicVO() == null) {
                return null;
            }
            return body.getMusicVO().getTitle();
        } catch (Exception e) {
            log.warn("query music title fail, soundId={}, msg={}", id, e.getMessage());
            return null;
        }
    }
}