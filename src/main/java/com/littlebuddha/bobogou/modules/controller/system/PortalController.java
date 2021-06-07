package com.littlebuddha.bobogou.modules.controller.system;

import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.system.Menu;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.Portal;
import com.littlebuddha.bobogou.modules.entity.system.utils.HomeInfo;
import com.littlebuddha.bobogou.modules.entity.system.utils.LogoInfo;
import com.littlebuddha.bobogou.modules.service.system.MenuService;
import com.littlebuddha.bobogou.modules.service.system.PortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 门户页controller
 */
@Controller
@RequestMapping("/portal")
public class PortalController extends BaseController {

    @Autowired
    private PortalService portalService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private LogoInfo logoInfo;//logo信息--保存在配置文件中

    @Autowired
    private HomeInfo homeInfo;//home信息--保存在配置文件中

    /**
     * 门户页面
     *
     * @return
     */
    @GetMapping(value = {"/", "/list"})
    public String list(HttpSession session, Model model) {
        Operator currentUser = UserUtils.getCurrentUser();
        session.setAttribute("currentUser", currentUser);
        //是否显示主页导航条的搜索框
        model.addAttribute("showNavSearch", true);
        return "modules/system/portal";
    }

    @ResponseBody
    @GetMapping("/data")
    public Portal data() {
        Portal portal = new Portal();
        portal.setHomeInfo(homeInfo);
        portal.setLogoInfo(logoInfo);
        List<Menu> menuInfo = menuService.findMenuInfo();
        portal.setMenuInfo(menuInfo);
        return portal;
    }

    @GetMapping("/importTemplate")
    public String importTemplate() {

        return "modules/common/import";
    }
}
