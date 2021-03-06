package cn.codertq.springrobot.mapper;

import cn.codertq.springrobot.domain.entity.CfUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CfUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cf_user
     *
     * @mbg.generated Mon Jan 24 10:50:12 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cf_user
     *
     * @mbg.generated Mon Jan 24 10:50:12 CST 2022
     */
    int insert(CfUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cf_user
     *
     * @mbg.generated Mon Jan 24 10:50:12 CST 2022
     */
    int insertSelective(CfUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cf_user
     *
     * @mbg.generated Mon Jan 24 10:50:12 CST 2022
     */
    CfUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cf_user
     *
     * @mbg.generated Mon Jan 24 10:50:12 CST 2022
     */
    int updateByPrimaryKeySelective(CfUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cf_user
     *
     * @mbg.generated Mon Jan 24 10:50:12 CST 2022
     */
    int updateByPrimaryKey(CfUser record);

    CfUser selectByQQNumber(@Param(value = "qq") long QQ);
}
