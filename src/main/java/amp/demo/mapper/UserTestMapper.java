package amp.demo.mapper;

import amp.demo.entity.ScheduleJob;
import amp.demo.entity.UserTest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface UserTestMapper {


    int insertCheckCupData(List<UserTest> checkCupDataBOList);

    int updateData(List<UserTest> checkCupDataBOList);
}
