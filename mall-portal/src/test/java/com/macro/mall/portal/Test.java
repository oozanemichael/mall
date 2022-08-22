package com.macro.mall.portal;

import cn.hutool.core.util.StrUtil;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.domain.MemberDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        UmsMember member=new UmsMember();
        member.setId(1L);
        member.setPhone("15330601136");
        member.setUsername("001");
        MemberDetails memberDetails=new MemberDetails(member);

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", memberDetails.getUsername());
        claims.put("created", new Date());

        String oldToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, "mall-portal-secret")
                .compact();
        System.out.println(oldToken);

        String tokenHead=new String("Bearer ");
        oldToken=tokenHead+oldToken;
        String token = oldToken.substring(tokenHead.length());
        if(StrUtil.isEmpty(token)){
            System.out.println("token校验不通过");
        }
        //token校验不通过
        Claims claimsssss = Jwts.parser()
                .setSigningKey("mall-portal-secret")
                .parseClaimsJws(token)
                .getBody();
        if(claimsssss==null){
            System.out.println("token校验不通过");
        }

    }

    public static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + 604800 * 1000);
    }

}




