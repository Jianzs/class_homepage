package top.zhengsj.klass.web.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhengsj.klass.pojo.dto.OptionDto;
import top.zhengsj.klass.pojo.dto.ResponseDto;
import top.zhengsj.klass.pojo.entity.UserEntity;
import top.zhengsj.klass.service.UserService;
import top.zhengsj.klass.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private HttpServletRequest request;

    @Autowired
    public UserController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @PostMapping("/login")
    public ResponseDto login(@RequestBody UserEntity user) {
        if (user.getNumber() == null ||
                user.getPassword() == null) {
            return ResponseDto.failed("Something is Blank.");
        }

        OptionDto<Integer, String> res = userService.frontLogin(user);
        if (res.getOptKey().equals(0)) {
            return ResponseDto.succeed().setData("token", res.getOptVal());
        } else {
            return ResponseDto.failed(res.getOptVal());
        }
    }

    @PutMapping("")
    public ResponseDto updateInfo(@RequestBody UserEntity user) {
        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        userService.frontUpdateInfo(userId, user);
        return ResponseDto.succeed();
    }

    @GetMapping("")
    public ResponseDto getInfo(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        UserEntity user = userService.getFrontUserInfo(userId);
        return ResponseDto.succeed().setData("info", user);
    }

    @PutMapping("/password")
    public ResponseDto changePassword(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.isNull("oldPassword") ||
                jsonObject.isNull("newPassword")) {
            return ResponseDto.failed("old-password or new-password can't be null");
        }
        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        OptionDto<Integer, String> res = userService.changePassword(userId, jsonObject);
        if (res == null) {
            return ResponseDto.succeed();
        } else  {
            return ResponseDto.failed(res.getOptVal()).setData("code", res.getOptKey());
        }
    }
}
