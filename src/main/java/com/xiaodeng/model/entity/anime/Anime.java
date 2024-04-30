package com.xiaodeng.model.entity.anime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XiaoDeng
 * @Date 2024/4/30 14:05
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Anime {

    /**
     * 角色名称
     */
    String name;

    /**
     * 动漫名称
     */
    String cartoonName;

}
