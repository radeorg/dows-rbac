package org.dows.rbac.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 初始化设置</ br>
 * @author: lait.zhang@gmail.com
 * @date: 3/21/2024 5:42 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Data
public class InitItem {
    private Boolean update = false;
    private String appId;
    // 有密钥才能更新
    private String appSecret;
    // 用","分割，该item 可以是 menu,uri,role等code
    private List<String> items = new ArrayList<>();

}

