package com.rxliuli.example.wxmpexample.util.wx;

import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

import java.util.List;

/**
 * @author rxliuli
 */
public class WxMenuButtonUtil {
    /**
     * 根据 wxMpService 和 menuId 查询一个菜单按钮
     *
     * @param wxMpService 微信 api 服务
     * @param menuId      菜单 id
     * @return 菜单对象
     */
    public static WxMenuButton findById(final WxMpService wxMpService, final String menuId) {
        try {
            final List<WxMenuButton> buttonList = wxMpService.getMenuService().menuGet().getMenu().getButtons();
            return findById(buttonList, menuId);
        } catch (WxErrorException e) {
            return null;
        }
    }

    /**
     * 根据列表和 menuId 递归查询指定的菜单/按钮
     *
     * @param wxMenuButtonList 菜单按钮列表
     * @param menuId           菜单 id
     * @return 查询到的菜单, 或者 null
     */
    private static WxMenuButton findById(List<WxMenuButton> wxMenuButtonList, String menuId) {
        for (WxMenuButton button : wxMenuButtonList) {
            if (button.getKey().equals(menuId)) {
                return button;
            }
            if (button.getSubButtons().isEmpty()) {
                continue;
            }
            return findById(button.getSubButtons(), menuId);
        }
        return null;
    }
}
