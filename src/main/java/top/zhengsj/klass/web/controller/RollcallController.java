package top.zhengsj.klass.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhengsj.klass.dao.projection.RollcallBriefProjection;
import top.zhengsj.klass.pojo.dto.ResponseDto;
import top.zhengsj.klass.service.RollcallService;
import top.zhengsj.klass.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rollcall")
public class RollcallController {
    private RollcallService rollcallService;

    @Autowired
    public RollcallController(RollcallService rollcallService) {
        this.rollcallService = rollcallService;
    }

    @GetMapping("/record")
    public ResponseDto getRecord(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);

        List<RollcallBriefProjection> records = rollcallService.getFrontRecord(userId);

        return ResponseDto.succeed().setData("records", records);
    }
}
