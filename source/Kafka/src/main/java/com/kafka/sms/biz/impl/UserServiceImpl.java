/***
 * @pName management
 * @name UserServiceImpl
 * @user DF
 * @date 2018/8/13
 * @desc
 */
package com.kafka.sms.biz.impl;

import com.google.common.collect.ImmutableMap;
import com.kafka.sms.biz.IUserService;
import com.kafka.sms.entity.db.MerchantMessage;
import com.kafka.sms.entity.db.Users;
import com.kafka.sms.entity.db.Wallets;
import com.kafka.sms.entity.dbExt.UserDetailInfo;
import com.kafka.sms.entity.resp.MerchantBusiness;
import com.kafka.sms.entity.resp.UserResp;
import com.kafka.sms.exception.InfoException;
import com.kafka.sms.exception.MsgException;
import com.kafka.sms.repository.MerchantMessageMapper;
import com.kafka.sms.repository.PayMapper;
import com.kafka.sms.repository.UserMapper;
import com.kafka.sms.repository.WalletMapper;
import com.kafka.sms.repository.utils.ConditionUtil;
import com.kafka.sms.utils.MD5Util;
import com.kafka.sms.utils.PropertyUtil;
import com.kafka.sms.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserServiceImpl extends BaseServiceImpl<Users> implements IUserService {
    private final UserMapper userMapper;
    private final WalletMapper walletMapper;
    private final PayMapper payMapper;
    private final MerchantMessageMapper merchantMessageMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, WalletMapper walletMapper, PayMapper payMapper, MerchantMessageMapper merchantMessageMapper) {
        this.userMapper = userMapper;
        this.walletMapper = walletMapper;
        this.payMapper = payMapper;
        this.merchantMessageMapper = merchantMessageMapper;
    }

    /**
     * 插入数据 韦德 2018年8月13日13:27:48
     *
     * @param param
     * @return
     */
    @Override
    @Transactional
    public void register(Users param) {
        // 增加用户数据
        int count = 0;
        try{
            count = userMapper.insert(param);
        }catch (Exception e){
            if(e != null  && e.getCause() != null  && e.getMessage() != null  && e.getCause().getMessage().contains("Duplicate entry")){
                String msg = "对不起，您注册的账户已存在！";
                if(e.getCause().getMessage().contains("uq_panda")){
                    msg = "对不起，您输入的熊猫id已存在！";
                }
                throw new MsgException(msg);
            }
        }
        if(count == 0) throw new MsgException("注册用户失败");

        // 开通钱包
        Wallets wallets = new Wallets();
        wallets.setUserId(param.getUserId());
        wallets.setBalance(0D);
        wallets.setUpdateTime(new Date());
        wallets.setVersion(0);
        count = 0;
        count = walletMapper.insert(wallets);
        if(count == 0) throw new MsgException("开通钱包失败");
    }

    /**
     * 根据主键id查询用户信息 韦德 2018年8月27日11:17:42
     *
     * @param userId
     * @return
     */
    @Override
    public UserResp getUserById(Integer userId) {
        UserDetailInfo userInfo = userMapper.selectUserDetail(userId.toString());
        if(userInfo == null) return null;
        UserResp userResp = new UserResp();
        PropertyUtil.clone(userInfo, userResp);
        return userResp;
    }

    /**
     * 根据用户名查询用户信息 韦德 2018年8月27日22:51:49
     *
     * @param username
     * @return
     */
    @Override
    public Users getUserByUserName(String username) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        Users users = new Users();
        users.setPhone(username);
        criteria.andEqualTo("phone", users.getPhone());
        List<Users> usersList = userMapper.selectByExample(example);
        if (users != null && usersList.size() > 0) {
            return usersList.get(0);
        }
        return null;
    }

    /**
     * 分页加载 韦德 2018年8月30日11:29:00
     *
     * @param page
     * @param limit
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List<UserDetailInfo> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime) {
        // 计算分页位置
        page = ConditionUtil.extractPageIndex(page, limit);
        String where = extractLimitWhere(condition, state, beginTime, endTime);
        List<UserDetailInfo> list = userMapper.selectLimit(page, limit, state, beginTime, endTime, where);
        return list;
    }

    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     *
     * @return
     */
    @Override
    public Integer getCount() {
        return userMapper.selectCount(new Users());
    }

    /**
     * 加载分页记录数 韦德 2018年8月30日11:29:22
     *
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public Integer getLimitCount(String condition, Integer state, String beginTime, String endTime) {
        String where = extractLimitWhere(condition, state, beginTime, endTime);
        return userMapper.selectLimitCount(state, beginTime, endTime, where);
    }

    /**
     * 更新密码 韦德 2018年9月1日00:23:54
     *
     * @param users
     */
    @Override
    public int updatePassword(Users users) {
        Users model = userMapper.selectByPrimaryKey(users);
        if(model == null) throw new MsgException("用户不存在");
        model.setPassword(MD5Util.md5(model.getPhone() + users.getPassword().trim()));
        model.setUpdateDate(new Date());
        int count = userMapper.updateByPrimaryKey(model);
        if(count == 0) throw new MsgException("更新失败");
        return count;
    }

    /**
     * 冻结用户 韦德 2018年9月1日00:28:07
     *
     * @param users
     */
    @Override
    public int updateEnable(Users users) {
        Users model = userMapper.selectByPrimaryKey(users);
        if(model == null) throw new MsgException("用户不存在");
        model.setUpdateDate(new Date());
        model.setIsEnable(users.getIsEnable());
        model.setIsDelete(users.getIsDelete());
        int count = userMapper.updateByPrimaryKey(model);
        if(count == 0) throw new MsgException("更新失败");
        return count;
    }

    /**
     * 登录验证并查询返回用户信息 韦德 2018年8月14日10:25:49
     *
     * @param user
     * @param smsCode
     * @return
     */
    @Override
    public UserResp loginAndQuery(Users user, String smsCode) {
        Users condition = new Users();
        condition.setPhone(user.getPhone());
        condition.setPassword(MD5Util.md5(user.getPhone() + user.getPassword()));
        condition.setIsDelete(0);
        condition.setIsEnable(1);
        Users userInfo = userMapper.selectOne(condition);

        if(userInfo == null) return null;
        if(userInfo.getIsEnable() == 0) throw new MsgException("您的账户已被管理员锁定");
        if(userInfo.getIsDelete() == 1) throw new MsgException("您的账户已被管理员回收");

        condition = new Users();
        condition.setUserId(userInfo.getUserId());
        condition.setIp(user.getIp());
        userMapper.updateOne(condition);

        Map<String, String> fields = ImmutableMap.of("phone", userInfo.getPhone(), "userId", userInfo.getUserId() + "", "timestamp", Instant.now().getEpochSecond() + "");
        String token = TokenUtil.create(fields);
        UserResp userResp = new UserResp();
        PropertyUtil.clone(userInfo, userResp);
        userResp.setToken(token);
        renewalToken(token , userInfo.getPhone(), userInfo.getUserId());
        return userResp;
    }

    /**
     * 登录验证 韦德 2018年8月26日17:18:58
     *
     * @param user
     * @return
     */
    @Override
    public Users login(Users user) {
        Users condition = new Users();
        condition.setPhone(user.getPhone());
        condition.setIsDelete(0);
        condition.setIsEnable(1);
        Users userInfo = userMapper.selectOne(condition);

        if(userInfo == null) return null;
        if(userInfo.getIsEnable() == 0) throw new MsgException("您的账户已被管理员锁定");
        if(userInfo.getIsDelete() == 1) throw new MsgException("您的账户已被管理员回收");

        condition = new Users();
        condition.setUserId(userInfo.getUserId());
        condition.setIp(user.getIp());
        userMapper.updateOne(condition);

        return userInfo;
    }

    /**
     * 令牌续期 韦德 2018年8月14日13:41:38
     * @param token
     * @param phone
     * @param userId
     */
    private void renewalToken(String token, String phone, Integer userId){
        String key  = "token:" + MD5Util.encrypt16(phone + userId);
        redisTemplate.opsForValue().set(key, token, 30, TimeUnit.DAYS);
    }

    /**
     * 根据令牌获取用户信息 韦德 2018年8月14日12:49:00
     *
     * @param token
     * @return
     */
    @Override
    public UserResp getUserByToken(String token) {
        Map<String, String> map = TokenUtil.validate(token);
        if(map.isEmpty()) return null;

        String phone = map.get("phone");
        String userId = map.get("userId");

        Users condition = new Users();
        condition.setPhone(phone);
        condition.setUserId(Integer.parseInt(userId));
        Users userInfo = userMapper.selectOne(condition);

        UserResp userResp = new UserResp();
        PropertyUtil.clone(userInfo, userResp);
        userResp.setToken(token);

        String key  = "token:" + MD5Util.encrypt16(phone + userId);
        Long expire = redisTemplate.getExpire(key);
        if(expire <= 0) throw new InfoException("登录令牌失效");
        renewalToken(token, userInfo.getPhone(), userInfo.getUserId());
        return userResp;
    }

    /**
     * 登出操作 韦德 2018年8月14日13:36:20
     *
     * @param token
     */
    @Override
    public void logout(String token) {
        Map<String, String> map = TokenUtil.validate(token);
        if(!map.isEmpty()){
            String key  = "token:" + MD5Util.encrypt16(map.get("phone") + map.get("userId"));
            redisTemplate.delete(key);
        }
    }

    /**
     * 根据令牌获取用户详细信息 韦德 2018年8月16日00:08:26
     *
     * @param token
     * @return
     */
    @Override
    public UserResp getUserDetailByToken(String token) {
        // 根据token查询用户权限字段
        Map<String, String> map = TokenUtil.validate(token);
        if(map.isEmpty()) return null;
        String phone = map.get("phone");
        String userId = map.get("userId");

        String key  = "token:" + MD5Util.encrypt16(phone + userId);
        Long expire = redisTemplate.getExpire(key);
        if(expire <= 0) throw new MsgException("登录令牌失效");

        // 查询基础信息
        UserDetailInfo userInfo = userMapper.selectUserDetail(userId);
        UserResp userResp = new UserResp();
        PropertyUtil.clone(userInfo, userResp);
        userResp.setToken(token);

        // 计算不可提现金额、可提现金额
        Double notWithdrawAmount = payMapper.selectNotWithdrawAmount(Integer.valueOf(userId),  "新用户注册奖励");
        Double withdrawAmount = payMapper.selectWithdrawAmount(Integer.valueOf(userId));
        if(withdrawAmount == null) withdrawAmount = 0D;
        userResp.setCanWithdrawAmount(withdrawAmount);
        userResp.setCanNotWithdrawAmount(notWithdrawAmount);
        return userResp;
    }

    /**
     * 修改密码 韦德 2018年8月17日00:35:30
     *
     * @param token
     * @param password
     * @param newPassword
     */
    @Override
    public boolean editPassword(String token, String password, String newPassword) {
        Map<String, String> map = TokenUtil.validate(token);
        if(map.isEmpty()) return false;
        String phone = map.get("phone");
        Integer userId = Integer.valueOf(map.get("userId"));

        Users users = userMapper.selectByPrimaryKey(userId);
        if(users == null) throw new MsgException("用户不存在");

        if(!users.getPassword().equalsIgnoreCase(MD5Util.md5(phone + password))) throw new MsgException("密码不正确");

        users.setPassword(MD5Util.md5(phone + newPassword));
        int count = userMapper.updateByPrimaryKey(users);
        if(count == 0) throw new MsgException("修改失败");

        String key  = "token:" + MD5Util.encrypt16(phone + userId);
        Long expire = redisTemplate.getExpire(key);
        if(expire <= 0) throw new MsgException("登录令牌失效");
        return true;
    }

    /**
     * 删除数据 韦德 2018年8月13日13:28:16
     *
     * @param param
     * @return
     */
    @Override
    public int updateAvailability(Users param) {
        Users model = userMapper.selectByPrimaryKey(param);
        if(model == null) throw new MsgException("用户不存在");
        /*model.setIsDelete(1);
        model.setIsEnable(0);*/
        model.setUpdateDate(new Date());
        model.setIsEnable(param.getIsEnable());
        model.setIsDelete(param.getIsDelete());
        int count = userMapper.updateByPrimaryKey(model);
        if(count == 0) throw new MsgException("更新失败");
        return count;
    }

    @Override
    @Transactional
    public int changeBalance(String username, Double amount) {
        Users user = this.getUserByUserName(username);
        if(user == null) throw new MsgException("用户不存在");
        user.setUpdateDate(new Date());
        userMapper.updateByPrimaryKey(user);
        Wallets wallets = walletMapper.selectByUid(user.getUserId());
        if(wallets == null) throw new MsgException("查询钱包失败");
        wallets.setBalance(wallets.getBalance() + amount);
        wallets.setUpdateTime(new Date());
        int count = walletMapper.updateByPrimaryKey(wallets);
        if(count == 0) throw new MsgException("更新失败");
        return count;
    }

    /**
     * 绑定财务账户 韦德 2018年9月2日00:57:46
     *
     * @param token
     * @param account
     */
    @Override
    public void bindFinanceAccount(String token, String account, String accountName) {
        Map<String, String> map = TokenUtil.validate(token);
        if(map.isEmpty()) throw new MsgException("请重新登录");
        Integer userId = Integer.valueOf(map.get("userId"));

        Users users = userMapper.selectByPrimaryKey(userId);
        if(users == null) throw new MsgException("查询用户信息失败");

        users.setFinanceId(account);
        users.setFinanceName(accountName);
        users.setUpdateDate(new Date());

        int count = userMapper.updateByPrimaryKey(users);
        if(count == 0) throw  new MsgException("绑定失败");
    }

    /**
     * 绑定熊猫麻将账户 韦德 2018年9月19日22:13:24
     *
     * @param token
     * @param account
     */
    @Override
    public void bindPandaAccount(String token, String account) {
        Map<String, String> map = TokenUtil.validate(token);
        if(map.isEmpty()) throw new MsgException("请重新登录");
        Integer userId = Integer.valueOf(map.get("userId"));

        Users users = userMapper.selectByPrimaryKey(userId);
        if(users == null) throw new MsgException("查询用户信息失败");

        users.setPandaId(account);
        users.setUpdateDate(new Date());

        int count = userMapper.updateByPrimaryKey(users);
        if(count == 0) throw  new MsgException("绑定失败");
    }

    /**
     * 设置商家权限 韦德 2018年10月21日10:57:58
     *
     * @param userId
     */
    @Override
    public void setMerchant(Integer userId) {
        Users users = userMapper.selectByPrimaryKey(userId);
        if(users.getLevel() == null || users.getLevel() == 0){
            users.setLevel(1);
        }else{
            users.setLevel(0);
        }
        int count = userMapper.updateByPrimaryKey(users);
        if(count <= 0) throw new MsgException("设置失败");
    }

    /**
     * 获取商家业务资金明细信息 韦德 2018年10月23日15:00:22
     *
     * @param token
     * @return
     */
    @Override
    public MerchantBusiness getMerchantBusinessList(String token) {
        Map<String, String> map = TokenUtil.validate(token);
        if(map.isEmpty()) throw new MsgException("请重新登录");
        Integer userId = Integer.valueOf(map.get("userId"));

        // 判断用户是否为代理商
        Users users = userMapper.selectByPrimaryKey(userId);
        if(users.getLevel() == null || !users.getLevel().equals(1)) throw new MsgException("请开通代理商权限后再来哦~");

        // 1.查询与此用户有关系的资金信息
        List<MerchantMessage> merchantMessages = merchantMessageMapper.selectChildren(userId);

        // 2.统计计算用户名下的资金、用户情况
        AtomicReference<Double> regiIncome = new AtomicReference<>(0D); // 注册推广收入
        AtomicReference<Double> returnIncome = new AtomicReference<>(0D);   // 游戏返利收入
        merchantMessages.stream().forEach(item -> {
            // 区分是推广收入还是游戏返利
            // type = 1 , 推广收入
            // type = 2 , 游戏返利
            if(item.getType().equals(1)){
                regiIncome.updateAndGet(v -> v + item.getAmount());
            }else if(item.getType().equals(2)){
                returnIncome.updateAndGet(v -> v + item.getAmount());
            }
        });
        List<Users> children = userMapper.selectChildren(userId);

        // 3.封装返回对象
        MerchantBusiness merchantBusiness = new MerchantBusiness();
        merchantBusiness.setRegiIncome(regiIncome.get());
        merchantBusiness.setReturnIncome(returnIncome.get());
        merchantBusiness.setMerchantMessageList(merchantMessages);
        merchantBusiness.setFriends(children.size());
        return merchantBusiness;
    }


    /**
     * 提取分页条件
     * @return
     */
    private String extractLimitWhere(String condition, Integer isEnable,  String beginTime, String endTime) {
        // 查询模糊条件
        String where = " 1=1";
        if(condition != null) {
            condition = condition.trim();
            where += " AND (" + ConditionUtil.like("user_id", condition, true, "t1");
            if (condition.split("-").length == 2){
                where += " OR " + ConditionUtil.like("add_time", condition, true, "t1");
                where += " OR " + ConditionUtil.like("update_time", condition, true, "t1");
            }
            where += " OR " + ConditionUtil.like("phone", condition, true, "t1");
            where += " OR " + ConditionUtil.like("panda_id", condition, true, "t1") + ")";
        }

        // 查询全部数据或者只有一类数据
        // where = extractQueryAllOrOne(isEnable, where);

        // 取两个日期之间或查询指定日期
        where = extractBetweenTime(beginTime, endTime, where);
        return where.trim();
    }


    /**
     * 提取两个日期之间的条件
     * @return
     */
    private String extractBetweenTime(String beginTime, String endTime, String where) {
        if ((beginTime != null && beginTime.contains("-")) &&
                endTime != null && endTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }else if (beginTime != null && beginTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }else if (endTime != null && endTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }
        return where;
    }


    /**
     * 查询全部数据或者只有一类数据
     * @return
     */
    private String extractQueryAllOrOne(Integer isEnable, String where) {
        if (isEnable != null && isEnable != 0){
            where += " AND t1.is_enable = #{isEnable}";
        }
        return where;
    }
}