package com.darian.schoolmanager.common.modle;

import com.darian.schoolmanager.common.utils.logger.LoggerContants;
import lombok.Data;
import org.slf4j.MDC;

import java.util.Date;
import java.util.List;

/***
 * layUI 的 Response 格式
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/9/16  23:02
 */
@Data
public class CustomerResponse<T> {
    private String code;
    private String msg;
    private long count;
    private T data;
    private Date time;
    private String traceId;


    public static <T> CustomerResponse<T> ok(String msg, T data) {
        CustomerResponse<T> customerResponse = new CustomerResponse<>();
        customerResponse.setCode("0");
        customerResponse.setMsg(msg);
        customerResponse.setData(data);
        customerResponse.setTime(new Date());
        customerResponse.setTraceId(MDC.get(LoggerContants.TRACE_ID_KEY));
        return customerResponse;
    }

    public static <T> CustomerResponse<T> ok(T data) {
        return ok("success", data);
    }

    public static CustomerResponse okNull() {
        return ok(null);
    }

    public static <T> CustomerResponse<T> error(String errorMsg) {
        return error(errorMsg, null);
    }

    public static <T> CustomerResponse<T> error(String errorMsg, T data) {
        CustomerResponse<T> customerResponse = new CustomerResponse<>();
        customerResponse.setCode("500");
        customerResponse.setMsg(errorMsg);
        customerResponse.setData(data);
        customerResponse.setTime(new Date());
        customerResponse.setTraceId(MDC.get(LoggerContants.TRACE_ID_KEY));
        return customerResponse;
    }

    public static <T> CustomerResponse<T> error(String code, String errorMsg, T data) {
        CustomerResponse<T> customerResponse = new CustomerResponse<>();
        customerResponse.setCode(code);
        customerResponse.setMsg(errorMsg);
        customerResponse.setData(data);
        customerResponse.setTime(new Date());
        customerResponse.setTraceId(MDC.get(LoggerContants.TRACE_ID_KEY));
        return customerResponse;
    }

    public static <T extends List<?>> CustomerResponse<T> okList(T list) {
        CustomerResponse<T> response = ok(list);
        response.setCount(list.size());
        return response;
    }


    public static <T extends List<?>> CustomerResponse<T> okPage(T list, long count) {
        CustomerResponse<T> response = ok(list);
        response.setCount(count);
        return response;
    }
}
