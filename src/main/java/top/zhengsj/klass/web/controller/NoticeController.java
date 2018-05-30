package top.zhengsj.klass.web.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zhengsj.klass.dao.projection.NoticeBriefProjection;
import top.zhengsj.klass.pojo.dto.ResponseDto;
import top.zhengsj.klass.service.NoticeService;

import java.util.Date;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    private NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("")
    public ResponseDto getNoticeList(@RequestParam("page") Integer page,
                                     @RequestParam("type") Integer type,
                                     @RequestParam(value = "limit", defaultValue = "8") Integer limit) {
        page--;

        Page<NoticeBriefProjection> notices = noticeService.getFrontNoticeList(type, PageRequest.of(
                page,
                limit,
                Sort.Direction.DESC,
                "endTime"
        ));

        return ResponseDto.succeed()
                .setData("notices", notices.getContent())
                .setData("count", notices.getTotalPages());
    }
}
