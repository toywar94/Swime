package com.swime.controller;


import com.swime.domain.GroupAttendVO;
import com.swime.domain.StudyListVO;
import com.swime.domain.StudyParamVO;
import com.swime.domain.WishStudyVO;
import com.swime.service.GroupAttendService;
import com.swime.service.StudyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/study")
@RestController
@Log4j
@AllArgsConstructor
public class StudyAttendController {

    private StudyService service;
    private GroupAttendService groupAttendService;

    // 스터디 명단에 해당 id가 있는지 확인
    @GetMapping(value="/getAttend/{grpSn}/{userId}/{stdSn}")
    public ResponseEntity<String> getAttend (@PathVariable("grpSn") long grpSn, @PathVariable("userId") String userId, @PathVariable("stdSn") long stdSn) {
        // 1. 그룹명단에 해당 id가 있는지 확인/ 없으면 group not attend 반환
        // userId, grpSn 필요
        GroupAttendVO groupAttendVO = new GroupAttendVO();
        groupAttendVO.setGrpSn(grpSn);
        groupAttendVO.setUserId(userId);

        GroupAttendVO groupAttend = groupAttendService.readByGrpSnUserId(groupAttendVO);

        if(groupAttend == null || !"GRUS01".equals(groupAttend.getStatus())) {
            log.info("그룹에 없다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return new ResponseEntity<>("group not attend", HttpStatus.OK);

        }else {
            // 2. 그룹에 가입되어있으면 해당 id가 스터디 명단에 있는지 확인
            // userId, stdSn 필요
            StudyParamVO studyParamVO = new StudyParamVO();
            studyParamVO.setStdSn(stdSn);
            studyParamVO.setUserId(userId);

            StudyListVO attendant = service.getAttendant(studyParamVO);

            // 3-1. 해당 스터디 명단에 없으면 not attend 반환
            if(attendant == null) {
                log.info("스터디에 없다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                return new ResponseEntity<>("not attend", HttpStatus.OK);

            } else {
                // 3-2. 있고, 탈퇴 상태면 not attend 반환
                if("STUS02".equals(attendant.getStatus())) {
                    log.info("탈퇴했다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    return new ResponseEntity<>("not attend", HttpStatus.OK);
                }

                // 3-3. 있고, 가입상태면 attend 반환
                if("STUS01".equals(attendant.getStatus())) {
                    log.info("이미 가입했다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    return new ResponseEntity<>("attend", HttpStatus.OK);
                }

                // 3-4. 있고, 검토중인 상태면 waiting 반환
                if("STUS03".equals(attendant.getStatus())) {
                    log.info("검토중이다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    return new ResponseEntity<>("waiting", HttpStatus.OK);
                }

                // 3-5. 있고, 영구탈퇴인 상태면 kicked 반환
                if("STUS04".equals(attendant.getStatus())) {
                    log.info("영구탈퇴!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    return new ResponseEntity<>("kicked", HttpStatus.OK);
                }

                // 다 아니면 실패 반환
                return new ResponseEntity<>("fail", HttpStatus.BAD_GATEWAY);
            }
        }
    }

    // 스터디 참가
    @PostMapping(value = "/attend", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> attend(@RequestBody StudyParamVO studyParam) {
        //1. 여기로 요청 보낼때 stdSn, userId 넘겨줘야함
        log.info("잘 왔니?======================================stdSn = " + studyParam.getStdSn());
        log.info("잘 왔니?======================================UserId = " + studyParam.getUserId());

        //2. 이미 참가명단에 있는지 확인
        StudyListVO attendant = service.getAttendant(studyParam);

        // 3-1. 없으면 참석상태로 추가
        if(attendant == null) {
            studyParam.setStatus("STUS01");
            service.registerAttendant(studyParam);

            return new ResponseEntity<>("success", HttpStatus.OK);
        }else {
            // 3-2. 있고, 탈퇴상태면 참석상태로 update
            if("STUS02".equals(attendant.getStatus())) {
                studyParam.setStatus("STUS01");
                service.modifyAttendant(studyParam);

                return new ResponseEntity<>("success", HttpStatus.OK);
            }

            // 3-3. 있고, 검토중이면 참석상태로 update
            if("STUS03".equals(attendant.getStatus())) {
                studyParam.setStatus("STUS01");
                service.modifyAttendant(studyParam);

                return new ResponseEntity<>("success", HttpStatus.OK);
            }

            // 그 외의 상태 (가입, 검토중, 영구탈퇴)는 참가 실패
            return new ResponseEntity<>("fail", HttpStatus.BAD_GATEWAY);
        }
    }

    // 스터디 탈퇴
    @PostMapping(value = "/cancel", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> cancel(@RequestBody StudyParamVO studyParam) {
        //1. 여기로 요청 보낼때 stdSn, userId 넘겨줘야함
        log.info("잘 왔니?======================================stdSn = " + studyParam.getStdSn());
        log.info("잘 왔니?======================================UserId = " + studyParam.getUserId());

        //2. 이미 참가명단에 있는지 확인
        StudyListVO attendant = service.getAttendant(studyParam);

        // 3. 참가명단에 있고, 가입상태이면 탈퇴 상태로 update
        if(attendant != null && "STUS01".equals(attendant.getStatus())) {
            studyParam.setStatus("STUS02");
            service.modifyAttendant(studyParam);

            return new ResponseEntity<>("success", HttpStatus.OK);
        }

        // 그 외의 상태 (가입, 검토중, 영구탈퇴)는 참가 실패
        return new ResponseEntity<>("fail", HttpStatus.BAD_GATEWAY);
    }
}
