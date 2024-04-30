package com.xiaodeng.controller;

import com.xiaodeng.common.Result;
import com.xiaodeng.model.dto.AnimeTraceRequest;
import com.xiaodeng.model.entity.anime.Anime;
import com.xiaodeng.service.AnimeTraceService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author XiaoDeng
 * @Date 2024/4/30 13:29
 **/
@Controller
@RestController
@RequestMapping("anime")
public class AnimeTraceController {


    @Resource
    AnimeTraceService animeTraceService;


    @PostMapping("/query")
    public Result<Anime> queryAnimeName(@RequestBody @NotNull AnimeTraceRequest animeTraceRequest) {

        return Result.success(animeTraceService.getAnimeTrace(animeTraceRequest.getImgUrl()));
    }
}
