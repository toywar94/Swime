package com.swime.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
@ToString
public class GroupCriteria {
    private int pageNum;
    private int amount;

    private String category;
    private String categoryVal;

    private String sigungu;
    private String sigunguVal;

    private String groupName;
    private String groupNameVal;

    private String order;
    private String orderVal;

    public GroupCriteria() {
        this(1, 6);
    }

    public GroupCriteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }

    public String getListLink() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
                .queryParam("pageNum", this.pageNum)
                .queryParam("amount", this.getAmount());
                //.queryParam("type", this.getType())
                //.queryparam("keyowrd", this.getKeyword());

        return builder.toUriString();
    }

//    public String[] getTypeArr() {
//        return type == null ? new String[] {} : type.split("");
//    }
}
