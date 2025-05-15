package bitc.fullstack503.ordernetserver.mapper;

import bitc.fullstack503.ordernetserver.entity.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

//    @Select("SELECT * FROM user_account WHERE user_id = #{userId}")
//    UserAccount findByUserId(String userId);

    @Select("""
    SELECT 
        ua.*, 
        b.branch_supervisor,
        b.branch_name,
        w.warehouse_name
    FROM user_account ua
    LEFT JOIN branch b ON ua.user_ref_id = b.branch_id
    LEFT JOIN warehouse w ON ua.user_ref_id = w.warehouse_id
    WHERE ua.user_id = #{userId}
    """)
    UserAccount findByUserId(String userId);
}