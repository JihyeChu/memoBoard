package com.sparta.memoboard.controller;

import com.sparta.memoboard.dto.MemoRequestDto;
import com.sparta.memoboard.dto.MemoResponseDto;
import com.sparta.memoboard.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("/memos") // 메모생성
    public MemoResponseDto creatMemo(@RequestBody MemoRequestDto requestDto) {
        //  RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // Memo Max ID Check
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        // DB 저장
        memoList.put(memo.getId(), memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    @GetMapping("/memos/{id}") // 메모 선택 조회
    public MemoResponseDto selectGetMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        if(memoList.containsKey(id)) {
            // 해당 메모 가져오기
            Memo memo = memoList.get(id);

            // Entity -> ResponseDto
            MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

            return memoResponseDto;
//            return memo.getId();
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


    @GetMapping("/memos") // 메모 조회
    public List<MemoResponseDto> getMemos(){
        // Map To List
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();

        return responseList;
    }

    @PutMapping("/memos/{id}") // 메모 수정
    public MemoResponseDto updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        if(memoList.containsKey(id)) {
            // 해당 메모 가져오기
            Memo memo = memoList.get(id);

            // 비밀번호 비교 후 memo 수정
            if(Objects.equals(memo.getPassword(), requestDto.getPassword())){
                memo.update(requestDto);
            }

            // Entity -> ResponseDto
            MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

            return memoResponseDto;
//            return memo.getId();
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/memos/{id}") // 메모 삭제
    public MemoResponseDto deleteMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        // 해당 메모가 DB에 존재하는지 확인
        if(memoList.containsKey(id)){
            // 해당 메모 가져오기
            Memo memo = memoList.get(id);

            // 비밀번호 비교 후 memo 삭제
            if(Objects.equals(memo.getPassword(), requestDto.getPassword())){
                memoList.remove(id);
            }

            // Entity -> ResponseDto
            MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

            return memoResponseDto;
        }else{
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}












