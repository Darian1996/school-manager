package com.darian.schoolmanager.common.modle;

import lombok.Getter;
import lombok.Setter;

/***
 * 分页展示的基础 Request
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/10/10  8:16
 */
@Getter
@Setter
public class BasePageRequest {

    private long limit;

    private long page;

    public void validator() {
        if (this.limit == 0) {
            this.limit = 10L;
        }
        if (this.page == 0) {
            this.page = 1L;
        }
    }
}
