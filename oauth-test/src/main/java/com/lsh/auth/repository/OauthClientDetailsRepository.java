package com.lsh.auth.repository;

import com.lsh.auth.entity.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/15 5:11 下午
 * @desc ：
 */
public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails,Long> {



    OauthClientDetails findByClientId(String clientID);


}
