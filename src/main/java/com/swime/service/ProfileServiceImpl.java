package com.swime.service;

import com.swime.domain.*;
import com.swime.mapper.MemberMapper;
import com.swime.mapper.ProfileMapper;
import com.swime.mapper.StudyListMapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@Service
//@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    @Setter(onMethod_ = @Autowired)
    private ProfileMapper mapper;
    @Setter(onMethod_ = @Autowired)
    private StudyListMapper listMapper;

    @Override
    public List<GroupVO> getOwnerGroupList(String id) {
        return mapper.ownerList(id);
    }

    @Override
    public GroupVO read(Long sn, String id) {
        return mapper.read(sn, id);
    }

    @Override
    public List<GroupVO> getJoinGroupList(String id) {
        return mapper.joinList(id);
    }

    @Override
    public List<GroupVO> getWishGroupList(String id) {
        return mapper.wishList(id);
    }

    @Override
    public List<GroupVO> ownerListWithPaging(String id, ProfileCriteria cri) {
        return mapper.ownerListWithPaging(id, cri);
    }

    @Override
    public List<GroupVO> joinListWithPaging(String id, ProfileCriteria cri) {
        return mapper.joinListWithPaging(id, cri);
    }

    @Override
    public List<GroupVO> wishListWithPaging(String id, ProfileCriteria cri) {
        return mapper.wishListWithPaging(id, cri);
    }

    @Override
    public int ownerListCount(String id) {
        return mapper.ownerListCount(id);
    }

    @Override
    public int joinListCount(String id) {
        return mapper.joinListCount(id);
    }

    @Override
    public int wishListCount(String id) {
        return mapper.wishListCount(id);
    }

    @Override
    public List<StudyVO> makeStudyList(String id, ProfileCriteria cri) {
        return mapper.makeStudyList(id, cri);
    }

    @Override
    public int makeStudyCount(String id) {
        return mapper.makeStudyCount(id);
    }

    @Override
    public GroupStudyListDTO makeBoth(String id, ProfileCriteria cri) {
        GroupStudyListDTO dto = new GroupStudyListDTO();

        List<StudyVO> list = mapper.makeStudyList(id, cri);

        list.forEach(study -> {
            study.setAttendants(listMapper.count(study.getSn()));
        });

        dto.setCount(mapper.makeStudyCount(id));
        dto.setList(list);
        return dto;
    }

    @Override
    public GroupStudyListDTO beforeBoth(String id, ProfileCriteria cri) {
        GroupStudyListDTO dto = new GroupStudyListDTO();
        List<StudyVO> list = mapper.beforeStudyList(id, cri);

        list.forEach(study -> {
            study.setAttendants(listMapper.count(study.getSn()));
        });

        dto.setCount(mapper.beforeStudyCount(id));
        dto.setList(list);

        return dto;
    }

    @Override
    public GroupStudyListDTO afterBoth(String id, ProfileCriteria cri) {
        GroupStudyListDTO dto = new GroupStudyListDTO();
        List<StudyVO> list = mapper.afterStudyList(id, cri);

        list.forEach(study -> {
            study.setAttendants(listMapper.count(study.getSn()));
        });

        dto.setCount(mapper.afterStudyCount(id));
        dto.setList(list);
        return dto;
    }

    @Override
    public GroupStudyListDTO wishBoth(String id, ProfileCriteria cri) {
        GroupStudyListDTO dto = new GroupStudyListDTO();
        List<StudyVO> list = mapper.wishStudyList(id, cri);

        list.forEach(study -> {
            study.setAttendants(listMapper.count(study.getSn()));
        });

        dto.setCount(mapper.wishStudyCount(id));
        dto.setList(list);
        return dto;
    }
}

