package com.swime.Service;

import com.swime.domain.*;
import com.swime.service.StudyService;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.jcp.xml.dsig.internal.dom.DOMSubTreeData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.swime.config.RootConfig.class})
@Log4j
public class StudyServiceTests {
    @Setter(onMethod_ = @Autowired)
    private StudyService service;

    @Test
    public void testExist() {
        log.info(service);
        assertNotNull(service);
    }

    @Test
    public void testRegister() {
        StudyVO study = new StudyVO();
        study.setGrpSn(7);
        study.setRepresentation("hong4258@service.com");
        study.setName("스터디 만들기 테스트");
        study.setStartDate("2021-04-06");
        study.setEndDate("2021-04-06");
        study.setStartTime("14:00:00");
        study.setEndTime("16:00:00");
        study.setRepeatCycle("");
        study.setRepeatDay("");
        study.setInformation("스터디 만들기 테스트입니다.스터디 만들기 테스트입니다.스터디 만들기 테스트입니다.스터디 만들기 테스트입니다.");
        study.setOnOff("STOF02");
        study.setOnUrl("");
        study.setPlaceId("구글 place id");
        study.setExpense("5000원");
        study.setCapacity(20);

        assert (service.register(study) == 2);
    }

    @Test
    public void testGetList() {
        List<StudyVO> list = service.getList();

        for (StudyVO li : list) {
            assert ("STST01".equals(li.getStatus()) || "STST03".equals(li.getStatus()));
        }
    }

    @Test
    public void testGetListWithPaging() {
        StudyCriteria cri = new StudyCriteria();
        cri.setPageNum(1);
        cri.setAmount(3);

        List<StudyVO> list = service.getList(cri);

        for (StudyVO li : list) {
            assert ("STST01".equals(li.getStatus()) || "STST03".equals(li.getStatus()));
        }
        assert (0 <= list.size() && list.size() <= 3);
    }

    @Test
    public void testGet() {
        StudyVO study = service.get(307L);

        assert (study != null && study.getSn() == 307);
    }

    @Test
    public void testModify() {
        StudyVO study = service.get(366L);

        if (study == null) return;

        study.setName("제목 수정수정합니다.");

        assert (service.modify(study) == 1);
        assert ("제목 수정수정합니다.".equals(service.get(366L).getName()));
    }

    @Test
    public void testEndStudy() {
        StudyParamVO param = new StudyParamVO();
        param.setStdSn(307L);

        assert (service.endStudy(param) == 1);
        assert ("STST03".equals(service.get(307L).getStatus()));
    }

    @Test
    public void testRemove() {
        StudyParamVO param = new StudyParamVO();
        param.setStdSn(307L);

        assert (service.remove(param) == 1);
        assert (service.get(307L) == null);
    }


    // WishStudy
    @Test
    public void testGetWishList() {
        StudyCriteria cri = new StudyCriteria(1, 3);

        List<StudyVO> list = service.getWishList(cri, "asdf@naver.com");

        assert (0 <= list.size() && list.size() <= 3);
    }

    @Test
    public void testGetWish() {
        StudyParamVO param = new StudyParamVO();
        param.setStdSn(82L);
        param.setUserId("aaa@naver.com");

        WishStudyVO wish = service.getWish(param);

        assert (wish != null && wish.getSn() == 82L && "jiho@naver.com".equals(wish.getUserId()));
    }

    @Test
    public void testRegisterWish() {
        WishStudyVO wish = new WishStudyVO();
        wish.setStdSn(82L);
        wish.setUserId("aaa@naver.com");

        StudyParamVO param = new StudyParamVO();
        param.setStdSn(82L);
        param.setUserId("aaa@naver.com");

        if(service.getWish(param) != null) return;

        assert (service.registerWish(wish) == 1);
        assert (service.getWish(param) != null);
    }

    @Test
    public void testRemoveWish() {
        WishStudyVO wish = new WishStudyVO();
        wish.setStdSn(368L);
        wish.setUserId("jiho@naver.com");
        service.registerWish(wish);

        StudyParamVO param = new StudyParamVO();
        param.setStdSn(368L);
        param.setUserId("jiho@naver.com");

        assert (service.removeWish(param) == 1);
        assert (service.getWish(param) == null);
    }

    //StudyList
    @Test
    public void testGetAttendList() {
        List<StudyListVO> list = service.getAttendantList(307L);
        if (list.size() == 0) return;

        for(StudyListVO li : list) {
            assert (li.getStdSn() == 307L);
        }
    }

    @Test
    public void testGetAttendListWithPaging() {
        StudyCriteria cri = new StudyCriteria(1, 3);

        List<StudyListVO> list = service.getAttendantList(cri, 307L);
        if (list.size() == 0) return;

        for(StudyListVO li : list) {
            assert (li.getStdSn() == 307L);
        }
        assert (0 <= list.size() && list.size() <= 3);
    }

    @Test
    public void testGetWaitingList() {
        List<StudyListVO> list = service.getWaitingList(308L);
        if (list.size() == 0) return;

        for(StudyListVO li : list) {
            assert (li.getStdSn() == 308L);
        }
    }

    @Test
    public void testGetWaitingListWithPaging() {
        StudyCriteria cri = new StudyCriteria(1, 3);

        List<StudyListVO> list = service.getWaitingList(cri, 308L);
        if (list.size() == 0) return;

        for(StudyListVO li : list) {
            assert (li.getStdSn() == 308L);
        }
        assert (0 <= list.size() && list.size() <= 3);
    }

    @Test
    public void testRegisterAttendant() {
        StudyParamVO param = new StudyParamVO();
        param.setStdSn(41L);
        param.setUserId("hong2841@service.com");
        param.setStatus("STUS01");

        if(service.getAnswer(param) != null) return;

        assert (service.registerAttendant(param) == 1);
        assert (service.getAttendant(param) != null);
    }

    @Test
    public void testGetAttendant() {
        StudyParamVO param = new StudyParamVO();
        param.setStdSn(41L);
        param.setUserId("hong2841@service.com");

        StudyListVO list = service.getAttendant(param);
        assert (list != null && list.getStdSn() == 41L && "hong2841@service.com".equals(list.getUserId()));

    }

    @Test
    public void testCountAttendants() {
        assert (service.countAttendants(82L) >= 0);
    }

    //StudySurvey
    @Test
    public void testGetSurveyList() {
        List<StudySurveyVO> list = service.getSurveyList(82L);

        for(StudySurveyVO li : list) {
            assert(li.getStdSn() == 82L);
        }
    }

    @Test
    public void testRegisterSurvey() {
        StudySurveyVO survey = new StudySurveyVO();
        survey.setStdSn(84L);
        survey.setQuestionSn(1);
        survey.setQuestion("각오한마디 부탁드립니다.");

        assert (service.registerSurvey(survey) == 1);
        assert (service.getSurveyList(84L).size() > 0);
    }

    @Test
    public void testRemoveSurvey() {
        assert (service.removeSurvey(84L) == 1);
        assert (service.getSurveyList(84L).size() == 0);
    }

    //StudyAnswer
    @Test
    public void testGetAnswer() {
        StudyParamVO param = new StudyParamVO();
        param.setStdSn(82L);
        param.setUserId("aaa@naver.com");

        List<StudyAnswerVO> list = service.getAnswer(param);

        for(StudyAnswerVO li : list) {
            assert (li.getStdSn() == 82L);
            assert ("aaa@naver.com".equals(li.getUserId()));
        }

        assert (list.get(list.size()-1).getQuestionSn() == list.size());
    }

    @Test
    public void testRegisterAnswer() {
        StudyAnswerVO answer = new StudyAnswerVO();
        answer.setStdSn(165L);
        answer.setUserId("jiho@naver.com");
        answer.setQuestionSn(1);
        answer.setQuestion("해당 스터디는 상황에따라 조금 더 진행될 수도 있는데 괜찮으십니까?");
        answer.setAnswer("네. 괜찮습니다.");

        int result = service.registerAnswer(answer);

        assert(result == 2 || result == -1);

        StudyParamVO param = new StudyParamVO();
        param.setStdSn(165L);
        param.setUserId("jiho@naver.com");
        assert(service.getAnswer(param).size() > 0);
    }

    @Test
    public void testRemoveAnswer() {
        StudyAnswerVO answer = new StudyAnswerVO();
        answer.setStdSn(84L);
        answer.setUserId("ffff@naver.com");
        answer.setQuestionSn(1);
        answer.setQuestion("해당 스터디는 상황에따라 조금 더 진행될 수도 있는데 괜찮으십니까?");
        answer.setAnswer("네. 괜찮습니다.");
        service.registerAnswer(answer);

        StudyParamVO param = new StudyParamVO();
        param.setStdSn(84L);
        param.setUserId("ffff@naver.com");

        assert (service.removeAnswer(param) == 1);
        assert(service.getAnswer(param).size() == 0);
    }
}
