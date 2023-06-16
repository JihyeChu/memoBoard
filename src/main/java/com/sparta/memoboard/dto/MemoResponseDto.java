package com.sparta.memoboard.dto;

import com.sparta.memoboard.entity.Memo;
import lombok.Getter;

@Getter
public class MemoResponseDto {

    private Long id;
    private String title;
    private String username;
    private String password;
    private String contents;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.username = memo.getUsername();
        this.password = memo.getPassword();
        this.contents = memo.getContents();
    }
}
