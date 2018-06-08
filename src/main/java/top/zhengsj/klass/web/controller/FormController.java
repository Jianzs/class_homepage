package top.zhengsj.klass.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhengsj.klass.dao.projection.FormDetailProjection;
import top.zhengsj.klass.dao.projection.FormListProjection;
import top.zhengsj.klass.exception.PostFormException;
import top.zhengsj.klass.pojo.dto.ResponseDto;
import top.zhengsj.klass.service.FormService;
import top.zhengsj.klass.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/form")
public class FormController {
    private FormService formService;

    @Autowired
    public FormController(FormService formService) {
        this.formService = formService;
    }

    @GetMapping("/{formId}")
    public ResponseDto getForm(@PathVariable("formId") Integer formId) {
        FormDetailProjection contentHtml = formService.getFrontFrom(formId);

        return ResponseDto.succeed().setData("formContent", contentHtml);
    }

    @PostMapping("/{formId}")
    public ResponseDto postForm(@PathVariable("formId") Integer formId,
                                @RequestBody String json,
                                HttpServletRequest request)
            throws PostFormException {
        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        formService.postFront(formId, json, userId);
        return ResponseDto.succeed();
    }

    @GetMapping("")
    public ResponseDto getFormList(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        List<FormListProjection> forms = formService.getFrontFormList(userId);
        return ResponseDto.succeed().setData("forms", forms);
    }
}
