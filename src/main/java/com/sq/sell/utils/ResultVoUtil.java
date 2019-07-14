package com.sq.sell.utils;

import com.sq.sell.VO.ResultVo;

/**
 *根据结果自动构建resultVo对象
 */
public class ResultVoUtil {

    public static ResultVo success(Object object)
    {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(0);
        resultVo.setMessage("success");
        resultVo.setData(object);
        return resultVo;
    }

    public static ResultVo success()
    {
        return ResultVoUtil.success(null);
    }

    public static ResultVo error(Integer code,String msg)
    {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMessage(msg);
        return resultVo;
    }

}
