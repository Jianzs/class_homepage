package top.zhengsj.klass.web.controller.admin;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhengsj.klass.pojo.dto.ResponseDto;
import top.zhengsj.klass.service.FormService;
import top.zhengsj.klass.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/admin/form")
public class FormAdminController {
    private FormService formService;

    @Autowired
    public FormAdminController(FormService formService) {
        this.formService = formService;
    }

    @PostMapping("")
    public ResponseDto createBackForm(@RequestBody String json, HttpServletRequest request) throws IOException {
        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        JSONObject jsonObject = new JSONObject(json);

        formService.createBackForm(jsonObject, userId);

        return ResponseDto.succeed();
    }
}
