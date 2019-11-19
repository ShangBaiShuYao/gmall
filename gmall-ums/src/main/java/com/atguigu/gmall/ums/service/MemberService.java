package com.atguigu.gmall.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.ums.entity.MemberEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 会员
 *
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-10-30 18:42:38
 */
public interface MemberService extends IService<MemberEntity> {

    PageVo queryPage(QueryCondition params);


    /*
     * @Description 数据验证功能
     * @Date   2019/11/18 16:47
     */
    Boolean checkData(String data, Integer type);

    /*
     * @Description 注册功能
     * @Date   2019/11/18 19:21
     */
    void register(MemberEntity memberEntity, String code);

    /*
     * @Description 根据用户名和密码查询用户
     * @Date   2019/11/18 20:04
     */
    MemberEntity queryuser(String username, String password);
}

