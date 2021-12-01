import React, { Fragment, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

export default function AdminMenuList(){

    const newAdminMenu = [
        {
          "id": "1",
          "name": "상품 관리",
          "description": "상품을 관리(등록, 수정, 삭제)할 수 있습니다.",
          "url": "/productcontrol"
        },
        {
          "id": "2",
          "name": "거래내역 확인",
          "description": "현재까지 이뤄진 거래내역을 확인할 수 있습니다.",
          "url": "/orderlist"
        },
        {
          "id": "3",
          "name": "사용자 관리",
          "description": "사용자를 관리할 수 있습니다",
          "url": "/userlist"
        }
      ];
      
    const adminMenuList = newAdminMenu.map((item, idx) => (
        <div className="login-form" key={idx}>
            <div className="mb-3">
                <h3>{item.name}</h3>
                <div>{item.description}</div>
            </div>
            <Link to={item.url}>
                <button type="button" className="btn btn-color-orange admin-submit">{item.name}</button>
            </Link>
        </div>));

    return(
            <div className="Container">
                {adminMenuList}    
            </div>

    );
}