import React, { useEffect, useState } from 'react';
import Header from '../../../components/layout/Header';
import Footer from '../../layout/Footer';
import Bread from '../../elements/ui/Bread';
import { Link } from 'react-router-dom';
import AdminMenuList from './AdminMenuList';

export default function Admin(){

    return(
        <div id="wrap">
            <Header/>
                <Bread breadName ="ADMIN" />
                    <AdminMenuList/>
            <Footer/>
        </div>
    );
}