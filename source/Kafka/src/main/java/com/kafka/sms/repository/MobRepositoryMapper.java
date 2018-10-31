/***
 * @pName Kafka
 * @name MobRepositoryMapper
 * @user HongWei
 * @date 2018/10/31
 * @desc
 */
package com.kafka.sms.repository;

import com.kafka.sms.entity.db.MobRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MobRepositoryMapper extends MyMapper<MobRepository> {
    /**
     * 获取手机号 2018年10月31日23:24:10
     * @param projects 项目ID, 格式: 10000,10001,10002
     * @param numberType 号码类型, 0=不限运营商\1=移动\2=联通\3=电信\4=国际\5=虚拟\6=非虚拟; 130-189是指定号段
     * @param quantity 次取号数量(缺省为1), 最大支持单次取号数量为100
     * @return
     */
    @Select("SELECT *\n" +
            "FROM `tb_mob_repository` AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(mob_id) FROM `tb_mob_repository`)-(SELECT MIN(mob_id) FROM `tb_mob_repository`))+(SELECT MIN(mob_id) FROM `tb_mob_repository`)) AS mob_id) AS t2\n" +
            "WHERE t1.mob_id >= t2.mob_id AND  AND phone LIKE '#{numberType}%'\n" +
            "ORDER BY t1.mob_id LIMIT #{quantity};")
    List<MobRepository> getPhone(@Param("projects") String projects, @Param("numberType") Integer numberType,  @Param("quantity") Integer quantity);
}
