/***
 * @pName proback
 * @name FinanceAuthenticationInterceptor
 * @user DF
 * @date 2018/8/5
 * @desc
 */
package com.kafka.sms.interceptor;

import com.kafka.sms.annotaion. FinanceToken;
import com.kafka.sms.entity.Constant;
import com.kafka.sms.exception.FinanceException;
import com.kafka.sms.utils.Base64Utils;
import com.kafka.sms.utils.RSAUtil;
import com.kafka.sms.utils.RequestUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/***
 * 财务验证拦截器
 */
public class FinanceAuthenticationInterceptor implements HandlerInterceptor {
    /**
     * 控制验签系统开闭
     */
    private final Boolean isOpen;

    public FinanceAuthenticationInterceptor(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isOpen){
            // 只拦截method级别的处理器
            if (!(handler instanceof HandlerMethod)) return true;
            // 只拦截financeToken注解过的方法
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 判断接口是否需要登录
            FinanceToken financeToken = method.getAnnotation(FinanceToken.class);
            if (financeToken != null){
                String url = RequestUtil.getParameters(request);
                String sign = request.getParameter("sign");
                try {
                    String encrypt = getEncrypt(url);
                    String decrypt = getDecrypt(sign);
                    System.err.println("请求参数:" + url);
                    System.err.println("请求验签:" + sign);
                    System.err.println("系统验签:" + encrypt);
                    System.err.println("系统验签解密结果:" + decrypt);
                    if(sign != null && decrypt.equals(url)) return true;
                } catch (Exception e) {
                    throw new FinanceException(e, FinanceException.Errors.SIGN_ERROR, "验签失败");
                }
                throw new FinanceException(FinanceException.Errors.SIGN_ERROR, "验签失败");
            }
        }
        return true;
    }



    /**
     * 快速加密
     * @param body
     * @return
     * @throws Exception
     */
    private String getEncrypt(String body) throws Exception {
        // formUid=1&toUid=2&amount=1.9&remark=充值&token=
        byte[] bytes = RSAUtil.encryptByPublicKey(body.getBytes(), Constant.FINANCE_PUB_KEY);
        return  Base64Utils.encode(bytes);
    }

    /**
     * 快速解密
     * @param body
     * @return
     * @throws Exception
     */
    private String getDecrypt(String body) throws Exception {
        byte[] decode = Base64Utils.decode(body);
        return new String(RSAUtil.decryptByPrivateKey(decode, Constant.FINANCE_PRIVATE_KEY), "UTF-8");
    }
}
