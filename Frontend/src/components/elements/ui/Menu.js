import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

export default function Menu(){

    const [ menuData, setMenuData ] = useState([]);

    const menuList = menuData.map(item => {
        return(
            <li key={item.id} className="px-4"><Link to={item.url}>{item.name}</Link></li>
        );
    });
    
    return(
        <div className="col-xl-8 col-lg-8 d-none d-lg-block">
            <div className=" main-menu  ">
                <nav>
                    <ul>
                        <li><Link to="/">HOME</Link></li>
                        <li><Link to="/productlist">SHOP</Link></li>
                        {localStorage.getItem('userId') && localStorage.getItem('email').slice(0, 5) === "admin" ? 
                            <li><Link to="/admin">ADMIN</Link></li>
                                :
                            ""}
                        {localStorage.getItem('userId') ? 
                            <li><Link to="/myaccount">MYPAGE</Link></li>
                                :
                            <li><Link to="/login">MYPAGE</Link></li>}
                        <li><Link to="road">찾아오시는 길</Link></li>
                    </ul>
                </nav>
            </div>
        </div>

    );
}