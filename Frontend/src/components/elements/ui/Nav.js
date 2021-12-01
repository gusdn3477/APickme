import { isWidthDown } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import Logo from './Logo';
import Menu from './Menu';
import SideMenu from './SideMenu';

export default function Nav(){

    const [scroll , setScroll] = useState(0);

    useEffect(() => {
        //const header = document.querySelector(".sticky-bar");
        window.addEventListener("scroll",handleScroll);

        return() => {
            window.removeEventListener("scroll",handleScroll);
        }
    }, []);

    const handleScroll = () => {
        setScroll(window.scrollY);
    }

   // console.log(scroll);
    return(

        <div className={ 
           scroll ?  "header-padding-1 sticky-bar header-res-padding clearfix stick"
                 :  "header-padding-1  header-res-padding clearfix stick"}
           >
            <div className="container-fluid">
                <div className="row">
                        <Logo/>
                        <Menu/>
                        <SideMenu/>
                </div>
            </div>
        </div>
    );
}