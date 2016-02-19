package cn.xn.job.console.mapper;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;
import cn.xn.job.console.model.UserInfo;
import cn.xn.job.console.model.UserInfoCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import java.util.List;

public interface UserInfoMapper extends BaseMapper {
    int countByExample(UserInfoCriteria example);

    int deleteByExample(UserInfoCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    List<UserInfo> selectByExampleWithRowbounds(UserInfoCriteria example, RowBounds rowBounds);

    List<UserInfo> selectByExample(UserInfoCriteria example);

    UserInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoCriteria example);

    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoCriteria example);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    List<UserInfo> selectLoginByExample(String userName);
}