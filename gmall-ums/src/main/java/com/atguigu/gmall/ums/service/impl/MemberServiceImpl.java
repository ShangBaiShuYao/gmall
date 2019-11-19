package com.atguigu.gmall.ums.service.impl;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.gmall.ums.dao.MemberDao;
import com.atguigu.gmall.ums.entity.MemberEntity;
import com.atguigu.gmall.ums.service.MemberService;

import javax.xml.crypto.Data;
import java.awt.geom.Dimension2D;
import java.util.Date;
import java.util.Dictionary;
import java.util.UUID;

@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageVo(page);
    }

    
    /*
     * @Description 数据验证功能
     * @Date   2019/11/18 13:23
     */
    @Override
    public Boolean checkData(String data, Integer type) {


        QueryWrapper<MemberEntity> wrapper = new QueryWrapper<>();

        switch (type) {
            case 1:
                wrapper.eq("username", data);
                break;
            case 2:
                wrapper.eq("mobile", data);
                break;
            case 3:
                wrapper.eq("email", data);
                break;
            default:
                return false;
        }
        return this.count(wrapper)==0;//如果等于0就是true,如果不能与0就return一个false
    }


    /*
     * @Description 注册功能
     * @Date   2019/11/18 19:22
     */
    @Override
    public void register(MemberEntity memberEntity, String code) {
        //1.校验验证码


        //2生成盐

        String salt = StringUtils.substring(UUID.randomUUID().toString(),0,6);
        memberEntity.setSalt(salt);

        //3.加盐加密(把加密后的密码设置成为密码)
        memberEntity.setPassword(DigestUtils.md5Hex(memberEntity.getPassword() + salt));

        //4.注册功能

        memberEntity.setLevelId(1L);
        memberEntity.setStatus(1);
        memberEntity.setCreateTime(new Date());
        memberEntity.setIntegration(0);
        memberEntity.setGrowth(0);
        this.save(memberEntity);

        //5.删除redis中的验证码
    }



    /*
     * @Description 根据用户名和密码查询用户
     * @Date   2019/11/18 20:05
     */
    @Override
    public MemberEntity queryuser(String username, String password) {
        //1.查询
        QueryWrapper<MemberEntity> queryWrapper = new QueryWrapper<>();
        MemberEntity memberEntity = this.getOne(queryWrapper.eq("username", username));

        //2.校验用户名,如果根据用户查询的用户不存在说明用户名不合法,抛出异常
        if(memberEntity == null)
        {
            throw  new IllegalArgumentException("用户名不合法!");
        }

        //3.校验密码
        password = DigestUtils.md5Hex(password+ memberEntity.getSalt());
        if (StringUtils.equals(password,memberEntity.getPassword()))
        {
            throw new IllegalArgumentException("密码不合法!");
        }

        //4.用户名和密码都正确

        return memberEntity;
    }
}