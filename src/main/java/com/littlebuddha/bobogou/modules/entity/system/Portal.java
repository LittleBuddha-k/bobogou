package com.littlebuddha.bobogou.modules.entity.system;


import com.littlebuddha.bobogou.modules.entity.system.utils.HomeInfo;
import com.littlebuddha.bobogou.modules.entity.system.utils.LogoInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 返回到首页的数据
 */
@Component
public class Portal {

    private HomeInfo homeInfo;
    private LogoInfo logoInfo;
    private List<Menu> menuInfo;

    public HomeInfo getHomeInfo() {
        if (homeInfo == null) {
            homeInfo = new HomeInfo();
        }
        return homeInfo;
    }

    public void setHomeInfo(HomeInfo homeInfo) {
        this.homeInfo = homeInfo;
    }

    public LogoInfo getLogoInfo() {
        if (logoInfo == null) {
            logoInfo = new LogoInfo();
        }
        return logoInfo;
    }

    public void setLogoInfo(LogoInfo logoInfo) {
        this.logoInfo = logoInfo;
    }

    public List<Menu> getMenuInfo() {
        return menuInfo;
    }

    public void setMenuInfo(List<Menu> menuInfo) {
        this.menuInfo = menuInfo;
    }
}