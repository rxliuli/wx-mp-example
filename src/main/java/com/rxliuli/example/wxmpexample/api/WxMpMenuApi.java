package com.rxliuli.example.wxmpexample.api;

import com.rxliuli.example.wxmpexample.util.id.IdWorker;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信公众号菜单
 *
 * @author rxliuli
 */
@RestController
@RequestMapping("/wx/menu/")
public class WxMpMenuApi extends WxMpBaseApi {
    /**
     * 创建一个默认的菜单
     *
     * @return 菜单 id
     */
    @GetMapping("create")
    public String createDefault() throws WxErrorException {
        final WxMenu wxMenu = new WxMenu();
        final WxMenuButton buttonLeft = new WxMenuButton();
        buttonLeft.setType(WxConsts.MenuButtonType.CLICK);
        buttonLeft.setName("点击");
        buttonLeft.setKey(IdWorker.getIdStr());

        final WxMenuButton buttonRight = new WxMenuButton();
        buttonRight.setType(WxConsts.MenuButtonType.VIEW);
        buttonRight.setName("链接");
        buttonRight.setUrl("https://blog.rxliuli.com");
        buttonRight.setKey(IdWorker.getIdStr());
        wxMenu.getButtons().add(buttonLeft);
        wxMenu.getButtons().add(buttonRight);
        return wxMpService.getMenuService().menuCreate(wxMenu);
    }
}
