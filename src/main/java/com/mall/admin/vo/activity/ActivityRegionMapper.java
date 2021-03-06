package com.mall.admin.vo.activity;

import com.mall.admin.vo.activity.ActivityRegion;
import com.mall.admin.vo.activity.ActivityRegionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivityRegionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_activity_region
     *
     * @mbggenerated Sat Jul 25 10:00:10 CST 2015
     */
    int countByExample(ActivityRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_activity_region
     *
     * @mbggenerated Sat Jul 25 10:00:10 CST 2015
     */
    int deleteByExample(ActivityRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_activity_region
     *
     * @mbggenerated Sat Jul 25 10:00:10 CST 2015
     */
    int deleteByPrimaryKey(Long activityRegionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_activity_region
     *
     * @mbggenerated Sat Jul 25 10:00:10 CST 2015
     */
    int insert(ActivityRegion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_activity_region
     *
     * @mbggenerated Sat Jul 25 10:00:10 CST 2015
     */
    int insertSelective(ActivityRegion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_activity_region
     *
     * @mbggenerated Sat Jul 25 10:00:10 CST 2015
     */
    List<ActivityRegion> selectByExample(ActivityRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_activity_region
     *
     * @mbggenerated Sat Jul 25 10:00:10 CST 2015
     */
    ActivityRegion selectByPrimaryKey(Long activityRegionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_activity_region
     *
     * @mbggenerated Sat Jul 25 10:00:10 CST 2015
     */
    int updateByExampleSelective(@Param("record") ActivityRegion record, @Param("example") ActivityRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_activity_region
     *
     * @mbggenerated Sat Jul 25 10:00:10 CST 2015
     */
    int updateByExample(@Param("record") ActivityRegion record, @Param("example") ActivityRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_activity_region
     *
     * @mbggenerated Sat Jul 25 10:00:10 CST 2015
     */
    int updateByPrimaryKeySelective(ActivityRegion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_activity_region
     *
     * @mbggenerated Sat Jul 25 10:00:10 CST 2015
     */
    int updateByPrimaryKey(ActivityRegion record);
}