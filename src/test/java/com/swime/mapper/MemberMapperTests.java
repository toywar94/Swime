package com.swime.mapper;

import com.swime.domain.MemberVO;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.swime.config.RootConfig.class})
@Log4j
public class MemberMapperTests {

    @Setter(onMethod_ = @Autowired)
    MemberMapper mapper;

    @Test
    public void getMapper(){
        log.info(mapper);
        Assert.assertNotNull(mapper);
    }

    @Test
    public void readTest(){
//        MemberVO memberVO = mapper.read("asd123@naver.com");
        MemberVO memberVO = mapper.read("qwer3871@naver.com");
        Assert.assertNotNull(memberVO);
    }

    @Test
    public void insertTest() {
        int random = (int)(Math.random()*10000);
        MemberVO memberVO = new MemberVO();
        memberVO.setId("qwer" + random + "@naver.com");
        memberVO.setName("홍길동" + random);
        memberVO.setPassword("qwer" + random);
        Assert.assertEquals(mapper.insert(memberVO),1);
    }

    @Test
    public void updateTest(){
        int random = (int)(Math.random()*10000);
        MemberVO memberVO = mapper.read("qwer2392@naver.com");
        memberVO.setPassword("updatepassword" + random);
        memberVO.setName("이름변경" + random);
        memberVO.setBirth("2010312");
        memberVO.setLastLoginDate(new Date());
        memberVO.setEmailAuth(new Date());
        Assert.assertEquals(mapper.update(memberVO),1);
        log.info(memberVO.getEmailAuth());
    }

    @Test
    public void updateTest2(){
        int random = (int)(Math.random()*10000);
        MemberVO memberVO = mapper.read("qwer9017@naver.com");
        memberVO.setPassword("updatepassword" + random);
        memberVO.setName("이름변경" + random);
        if(memberVO.getLastLoginDate() == null) memberVO.setLastLoginDate(null);
        if(memberVO.getEmailAuth() == null) memberVO.setLastLoginDate(null);
        Assert.assertEquals(mapper.update(memberVO),1);
        log.info(memberVO.getEmailAuth());
    }

    @Test
    public void deleteTest(){
        int random = (int)(Math.random()*10000);
        MemberVO memberVO = new MemberVO();
        memberVO.setId("qwer" + random + "@naver.com");
        memberVO.setName("홍길동" + random);
        memberVO.setPassword("qwer" + random);
        Assert.assertEquals(mapper.insert(memberVO),1);
        Assert.assertEquals(mapper.delete("qwer" + random + "@naver.com"),1);
        Assert.assertNull(mapper.read("qwer" + random + "@naver.com"));
    }

    @Test
    public void getList(){
        mapper.getlist().forEach(log::info);
        Assert.assertNotNull(mapper.getlist());
    }


}