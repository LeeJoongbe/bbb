package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {


    @Builder.Default
    private int page = 1;//현재페이지 없다면 디폴트로 1

    @Builder.Default
    private int size = 10;//한페이지에 사이즈

    private String type;//검색타입

    private String keyword;//검색어


    private String link;



    public String[] getTypes(){
        if(type == null || type.isEmpty()){
            return null;
        }

        return type.split("");
    }


    public Pageable getPageable(String... props){
        return PageRequest.of(this.page-1, this.size, Sort.by(props).descending());

    }

    public String getLink(){
        if(link == null){
            StringBuffer builder = new StringBuffer();
            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);

            if(type != null && type.length()>0){
                builder.append("&type=" + type);
            }
            if(keyword != null){
                try {
                    builder.append("&keyword=" + URLEncoder.encode(keyword,"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            link = builder.toString();
        }

        return link;
    }

}
