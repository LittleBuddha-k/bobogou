package com.littlebuddha.bobogou.modules.controller.system;

import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.service.system.OperatorService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 */
@Controller
@RequestMapping("/system")
public class LoginController extends BaseController {

    @Autowired
    private OperatorService operatorService;

    /**
     * 登录页面
     * @return
     */
    @GetMapping(value = {"/","/loginPage"})
    public String loginPage() {
        return "modules/system/loginPage";
    }

    /**
     * 处理登录数据请求
     * @param username
     * @param password
     */
    @ResponseBody
    @PostMapping("/login")
    public Result login(String username, String password,String rememberMe){
        Result result = null;
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken authenticationToken = new UsernamePasswordToken(username, password);
        if("true".equals(rememberMe)){
            authenticationToken.setRememberMe(true);
        }
        try{
            subject.login(authenticationToken);
            result = new Result("200","登录成功");
            return result;
        }catch (UnknownAccountException unknownAccountException){
            result = new Result("301","用户名错误");
            return result;
        }catch (IncorrectCredentialsException incorrectCredentialsException){
            result = new Result("302","密码错误");
            return result;
        }
    }

    /**
     * 注册页面
     * @return
     */
    @GetMapping("/registerPage")
    public String registerPage() {
        return "modules/system/registerPage";
    }

    /**
     * 注册
     * @param operator
     * @return
     */
    @ResponseBody
    @PostMapping("/register")
    public Result register(Operator operator){
        Result result = null;
        int save = operatorService.register(operator);
        if(save > 0){
            result = new Result("200","注册成功");
        }else if (save == 0){
            result = new Result("304","用户名已存在");
        }else {
            result = new Result("305","注册失败");
        }
        return result;
    }

    /**
     * 退出登录
     * @return
     */
    @ResponseBody
    @PostMapping("/logout")
    public Result logout(){
        Result result = null;
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        result = new Result("303","退出成功");
        return result;
    }
}
