package com.rxliuli.example.wxmpexample.api;

import com.rxliuli.example.wxmpexample.util.id.IdWorker;
import com.rxliuli.example.wxmpexample.util.spring.SpringServletUtil;
import com.rxliuli.example.wxmpexample.util.wx.WxMenuButtonUtil;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 微信公众号菜单 api
 * 这里是给后台使用的, 用以创建, 修改, 删除, 查询菜单
 *
 * @author rxliuli
 */
@RestController
@RequestMapping("/wx/menu/")
public class WxMpMenuApi extends WxMpBaseApi {
    /**
     * 创建一个菜单
     *
     * @param wxMenu 菜单对象
     * @return 菜单的 id
     * @throws WxErrorException 微信错误
     */
    @PostMapping("create")
    public String menuCreate(@RequestBody WxMenu wxMenu) throws WxErrorException {
        return getMenuService().menuCreate(wxMenu);
    }

    /**
     * 创建一个默认的菜单
     *
     * @return 菜单 id
     */
    @GetMapping("create")
    public String menuCreateDefault() throws WxErrorException {
        final WxMenu wxMenu = new WxMenu();
        final WxMenuButton buttonLeft = new WxMenuButton();
        buttonLeft.setType(WxConsts.MenuButtonType.CLICK);
        buttonLeft.setName("点击");
        buttonLeft.setKey(IdWorker.getIdStr());

        final WxMenuButton buttonCenter = new WxMenuButton();
        buttonCenter.setType(WxConsts.MenuButtonType.VIEW);
        buttonCenter.setName("链接");
        buttonCenter.setUrl("https://github.com");
        buttonCenter.setKey(IdWorker.getIdStr());

        final WxMenuButton buttonRight = new WxMenuButton();
        buttonRight.setType(WxConsts.MenuButtonType.CLICK);
        buttonRight.setName("父菜单");
        buttonRight.setKey(IdWorker.getIdStr());

        final WxMenuButton buttonRightSubA = new WxMenuButton();
        buttonRightSubA.setType(WxConsts.MenuButtonType.CLICK);
        buttonRightSubA.setName("子菜单 A");
        buttonRightSubA.setKey(IdWorker.getIdStr());

        final WxMenuButton buttonRightSubB = new WxMenuButton();
        buttonRightSubB.setType(WxConsts.MenuButtonType.VIEW);
        buttonRightSubB.setName("子菜单 B");
        buttonRightSubB.setKey(IdWorker.getIdStr());

        //构建一个需要微信用户认证的链接
        final HttpServletRequest request = SpringServletUtil.getCurrentRequest();
        try {
            final URL requestUrl = new URL(request.getRequestURL().toString());
            final String url = wxMpService.oauth2buildAuthorizationUrl(String.format("%s://%s/wx/user/", requestUrl.getProtocol(), requestUrl.getHost()), WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
            buttonRightSubB.setUrl(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        buttonRight.getSubButtons().add(buttonRightSubA);
        buttonRight.getSubButtons().add(buttonRightSubB);

        wxMenu.getButtons().add(buttonLeft);
        wxMenu.getButtons().add(buttonCenter);
        wxMenu.getButtons().add(buttonRight);
        return getMenuService().menuCreate(wxMenu);
    }

    /**
     * 根据 json 串创建菜单
     *
     * @param json 菜单 json 字符串
     * @return 菜单 id
     */
    @GetMapping("create/{json}")
    public String menuCreate(@PathVariable("json") String json) throws WxErrorException {
        return getMenuService().menuCreate(json);
    }

    /**
     * 删除整个菜单
     */
    @GetMapping("delete")
    public void menuDelete() throws WxErrorException {
        getMenuService().menuDelete();
    }

    /**
     * 获取整个菜单
     *
     * @return 整个菜单对象
     */
    @GetMapping("get")
    public WxMpMenu get() throws WxErrorException {
        return getMenuService().menuGet();
    }

    /**
     * 根据 id 查询菜单按钮
     *
     * @param menuButtonId 菜单按钮 id
     * @return 查询到的菜单按钮
     */
    @GetMapping("get/{menuId}")
    public WxMenuButton get(@PathVariable("menuId") String menuButtonId) throws WxErrorException {
        return WxMenuButtonUtil.findById(wxMpService, menuButtonId);
    }

    /**
     * 根据菜单 id 删除菜单
     *
     * @param menuId 菜单 id
     */
    @GetMapping("delete/{menuId}")
    public void menuDelete(@PathVariable("menuId") String menuId) throws WxErrorException {
        getMenuService().menuDelete(menuId);
    }

    private WxMpMenuService getMenuService() {
        return wxMpService.getMenuService();
    }
}
