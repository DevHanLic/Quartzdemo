package amp.demo.mapper;

import amp.demo.entity.ScheduleJob;
import amp.demo.entity.UserTest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserTestMapper {

    int insertCheckCupData(List<UserTest> checkCupDataBOList);


}
